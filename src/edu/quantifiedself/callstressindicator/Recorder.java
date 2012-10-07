package edu.quantifiedself.callstressindicator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

import edu.mit.media.funf.storage.DatabaseService;
import edu.mit.media.funf.storage.NameValueDatabaseService;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
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
        public String phone = "";
        public long callStart = 0;
        public long callEnd = 0;
       
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
        
        //take the average "score"
        public float normalizeRms(ArrayList<Double> rmsValues){
        	double sum = 0;
        	for(int pos=0;pos < rmsValues.size();pos++){
        		sum += rmsValues.get(pos);
        	}
        	return (float)(sum / rmsValues.size());
        }
        
        public void record(String phone){
        		this.phone = phone;
        		this.callStart = System.currentTimeMillis();
                this.recordTask = new RecordAudio();
                this.recordTask.execute();
        }
       
        public void stoprecording(){
    		this.callEnd = System.currentTimeMillis();
            this.isRecording = false;
        }
        
        private void writeToFunf(ArrayList<Double> data){
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

		public void writeLocally(ArrayList<Double> data) {
	        LocalDatabaseHandler db = new LocalDatabaseHandler(context);
	        SettingsDatabaseHandler settingsDb = new SettingsDatabaseHandler(context);
        	CallData callData = new CallData();
        	callData.setPhone(phone);
        	callData.setTimestamp(new Date(callStart));
        	callData.setDuration((int)(callEnd - callStart));
        	CallSettings settings = settingsDb.getSettings();
        	double [] newValues = DataAnalysis.minMaxAvg(data);
        	double min, max, avg;
        	if(settings == null){
        		settings = new CallSettings();
        		settings.setAvg(newValues[2]);
        		settings.setMin(newValues[0]);
        		settings.setMax(newValues[1]);
        		min = settings.getMin();
        		max = settings.getMax();
        		avg = settings.getAvg();
        	}
        	else{
        		min = DataAnalysis.updateValues(newValues[0], settings.getMin());
        		max = DataAnalysis.updateValues(newValues[1], settings.getMax());
        		avg = DataAnalysis.updateValues(newValues[2], settings.getAvg());
        		settingsDb.updateCallData(min, max, avg);
        	}
        	float score = DataAnalysis.getScaledScore(data, min, max, avg);
        	callData.setRmsMedion(score);
	        Log.d("Insert: ", "Inserting ..");	        
	        db.addCallData(callData);
	        
	        List<CallData> savedData =  db.getAllCallData();
	        for (int i = 0; i < savedData.size(); i++) {
	        	CallData tempCallData = savedData.get(i); 
				Log.i("savedData", tempCallData.ToString());
			} ;
	        db.close();
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
                    writeLocally(data);
                    Log.d(TAG, "Insert to database Success");
                }catch(Throwable t){
                    Log.e(TAG, t.getMessage());
                }
                    return null;
            }
               
        }

       
}
