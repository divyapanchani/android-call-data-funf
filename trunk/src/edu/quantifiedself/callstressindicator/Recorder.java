package edu.quantifiedself.callstressindicator;

import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;

import edu.mit.media.funf.storage.DatabaseService;
import edu.mit.media.funf.storage.NameValueDatabaseService;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.os.AsyncTask;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.util.Log;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class Recorder {
       
        int audioSource;
        int sampleRateinHz;
        int channelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO;
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
        int bufferSizeInBytes;
        AudioRecord audioRecord;
        Boolean isRecording = false;
        RecordAudio recordTask;
        int blocksize = 256;
        private Context context;
        private String TAG;
        private String incomingNumber;
       
        public Recorder(int audioSource, int sampleRateinHz, Context context, String tag){
               
                this.audioSource = audioSource;
                this.sampleRateinHz = sampleRateinHz;
                this.bufferSizeInBytes = AudioRecord.getMinBufferSize(this.sampleRateinHz,
                													  this.channelConfig,
                													  this.audioFormat);
                this.context = context;
                this.TAG = tag;
        }
       
        public double rms(double[] nums){
            double ms = 0;
            for (int i = 0; i < nums.length; i++)
                ms += nums[i] * nums[i];
            ms /= nums.length;
            return Math.sqrt(ms);
        }

        public void record(String incomingNumber){
        		this.incomingNumber = incomingNumber
                this.recordTask = new RecordAudio();
                this.recordTask.execute();
        }
       
        public void stoprecording(){
                this.isRecording = false;
        }
        
        private void writeToFunf(ArrayList<Double> data){
        	// TODO grapse sti gamw malakia
        	Intent i = new Intent(context, DatabaseService.class);
        	i.setAction(DatabaseService.ACTION_RECORD); // The default action if none is specified
        	Bundle b = new Bundle();
        	b.putString(NameValueDatabaseService.DATABASE_NAME_KEY, MainPipeline.DEFAULT_PIPELINE_NAME);
        	b.putLong(NameValueDatabaseService.TIMESTAMP_KEY, System.currentTimeMillis());
        	double [] data_array = new double[data.size()];
        	for(int pos=0;pos < data.size();pos++)
        		data_array[pos] = data.get(pos);
        	b.putDoubleArray("MALAKIES", data_array);
        	i.putExtras(b);
        	context.startService(i);
        	Log.i(TAG, "O FIAS");
        }
        
        private double aggregateCallData(ArrayList<Double> data){
        	double [] data_array = new double[data.size()];
        	for(int pos=0;pos < data.size();pos++)
        		data_array[pos] = data.get(pos);
        	double [] minMaxAvg = DataAnalysis.minMaxAvg(data_array);
        	
        	return 1.0;
        }
        
        private class RecordAudio extends AsyncTask<Void, Integer, Void>{
               
            @Override
        	protected Void doInBackground(Void... params){
                isRecording = true;
                try{
                    audioRecord = new AudioRecord(audioSource,
                                                  sampleRateinHz,
                                              	  channelConfig,
                                                  audioFormat,
                                                  bufferSizeInBytes);
                    short[] buffer = new short[blocksize];
                    double[] toTransform = new double[blocksize];
                    audioRecord.startRecording();
                    ArrayList<Double> data = new ArrayList<Double>();
                    while(isRecording){
                            int bufferReaderResult = audioRecord.read(buffer, 0, blocksize);
                            for(int i = 0; i < blocksize && i < bufferReaderResult; i ++){
                                    toTransform[i] = (double) buffer[i] / 32678.0; // signed 16 bit
                            }
                            data.add(rms(toTransform));
                    }
                    audioRecord.stop();
                    writeToFunf(data);
                    Log.d(TAG, "Insert to database Success");
                }catch(Throwable t){
                    Log.e(TAG, "Recording Failed");
                }
                    return null;
            }
               
        }
       
}
