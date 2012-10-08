package edu.quantifiedself.callstressindicator;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class TelephonyListener extends PhoneStateListener{
	
	private static final String TAG = TelephonyListener.class.getName();
	private int CurState;
	private Recorder  myAudioRecorder;
	private int SampleRate = 8000;
	private int AudioSource = 1;
	private Context context;
	
	
	public TelephonyListener(int CurState, Context context){
		super();
		this.CurState = CurState;
		this.context = context;
		myAudioRecorder = new Recorder(AudioSource, SampleRate, this.context, TAG);
	}
	
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
    	 Log.i(TAG, "onCallStateChanged");
        super.onCallStateChanged(state, incomingNumber);
        if(CurState == state)
            return;
        CurState = state;
        switch (state) {
        case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.i(TAG, "OFF HOOK: " + incomingNumber);
                // TODO start recording edw
                break;
        case TelephonyManager.CALL_STATE_RINGING:
        		Log.i(TAG, "RINGING " + incomingNumber);
        		startRecording(incomingNumber);
                break;
        case TelephonyManager.CALL_STATE_IDLE:
        	stopRecording();
            break;          
        }
    }
	
    private void stopRecording() {
		// TODO Auto-generated method stub
		// stop recorder
    	myAudioRecorder.stoprecording();
	}

	private void startRecording(String incomingNumber){
		myAudioRecorder.record(incomingNumber);
    }

}
