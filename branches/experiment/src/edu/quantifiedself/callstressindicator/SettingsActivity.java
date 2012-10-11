package edu.quantifiedself.callstressindicator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemSelectedListener;


public class SettingsActivity extends Activity{

	Recorder recorder;
	Chronometer chronometer;
	Spinner sampleRateSpinner, audioSourceSpinner;
	public static int AudioSource = 1; //MIC
	public static int SampleRate = 8000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		chronometer = (Chronometer) findViewById(R.id.chronometer);

		sampleRateSpinner = (Spinner) findViewById(R.id.sample_rate_spinner);
		ArrayAdapter<CharSequence> sampleRateAdapter = ArrayAdapter.createFromResource(this, R.array.sample_rates_array,
				android.R.layout.simple_spinner_item);
		sampleRateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		sampleRateSpinner.setAdapter(sampleRateAdapter);
//		sampleRateSpinner.setOnItemSelectedListener(new SampleRateSelectedListener());
//		sampleRateSpinner.setSelection(4);


		audioSourceSpinner = (Spinner) findViewById(R.id.audio_source_spinner);
		ArrayAdapter<CharSequence> audioSourceAdapter = ArrayAdapter.createFromResource(this, R.array.audio_source_array,
				android.R.layout.simple_spinner_item);
		audioSourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		audioSourceSpinner.setAdapter(audioSourceAdapter);
//		audioSourceSpinner.setOnItemSelectedListener(new AudioSourceSelectedListener());
//		audioSourceSpinner.setSelection(0);
	}

	public void onToggleRecordClicked(View view) {
	}

	public void onToggleServiceClicked(View view) {
		// Perform action on clicks
	}
	public class AudioSourceSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//			String selectedSource = parent.getItemAtPosition(pos).toString();
//			AudioSources as = Utilities.AudioSources.valueOf(selectedSource);
//			AudioSource = as.getCode();
		}
		public void onNothingSelected(AdapterView parent) {
			// Do nothing.
		}
	}

	public class SampleRateSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			String selectedSampleRate = parent.getItemAtPosition(pos).toString();
			SampleRate = Integer.parseInt(selectedSampleRate);
		}
		public void onNothingSelected(AdapterView parent) {
			// Do nothing.
		}
	}
}
