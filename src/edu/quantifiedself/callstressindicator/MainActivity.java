package edu.quantifiedself.callstressindicator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.androidplot.series.XYSeries;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;

public class MainActivity extends FragmentActivity {

	// Settings
	Date FromDate, ToDate;
	String DatePeriod, ChartUnit;
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private XYPlot mySimpleXYPlot;
	Spinner datePeriodSpinner, chartUnitSpinner;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_main);
		context = this;

		 Button showChart = (Button)findViewById(R.id.showChart);

		// Initialize spinners
		datePeriodSpinner = (Spinner) findViewById(R.id.date_period_spinner);
		ArrayAdapter<CharSequence> sampleRateAdapter = ArrayAdapter.createFromResource(this, R.array.date_periods_array,
				android.R.layout.simple_spinner_item);
		sampleRateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		datePeriodSpinner.setAdapter(sampleRateAdapter);
		datePeriodSpinner.setOnItemSelectedListener(new DatePeriodSelectedListener());
		datePeriodSpinner.setSelection(1);

		chartUnitSpinner = (Spinner) findViewById(R.id.chart_unit_spinner);
		ArrayAdapter<CharSequence> audioSourceAdapter = ArrayAdapter.createFromResource(this, R.array.chart_units_array,
				android.R.layout.simple_spinner_item);
		audioSourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		chartUnitSpinner.setAdapter(audioSourceAdapter);
		chartUnitSpinner.setOnItemSelectedListener(new ChartUnitSelectedListener());
		chartUnitSpinner.setSelection(1);
		// end

		// default is one week
		setPeriodLastWeek();
		// default
		ChartUnit = "Per day";
		// Initialize chart
		createChart(FromDate, ToDate, ChartUnit);
		
		showChart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				createChart(FromDate, ToDate, ChartUnit);
			}
		});


	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_menu, menu);
	    return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
 
        switch (item.getItemId())
        {
        
        case R.id.settings:
			Intent sIntent = new Intent(this, SettingsActivity.class);
			startActivityForResult(sIntent, 0);
            return true;
 
        case R.id.main:
			Intent mIntent = new Intent(this, MainActivity.class);
			startActivityForResult(mIntent, 0);
            return true;
 
        case R.id.help:
            return true;

 
        default:
            return super.onOptionsItemSelected(item);
        }
    }    
	private void setPeriodLastWeek() {
		FromDate = Calendar.getInstance().getTime();
		FromDate.setDate(Calendar.getInstance().getTime().getDate() - 7);
		ToDate = Calendar.getInstance().getTime();
		DatePeriod = "Last week";
	}

	private void setPeriodLastMonth() {
		FromDate = Calendar.getInstance().getTime();
		FromDate.setMonth(Calendar.getInstance().getTime().getMonth() - 1);
		ToDate = Calendar.getInstance().getTime();
		DatePeriod = "Last month";
	}

	private void createChart(Date from, Date to, String unit) {
		// initialize our XYPlot reference:
		mySimpleXYPlot = (XYPlot) findViewById(R.id.startXYPlot);
		mySimpleXYPlot.clear();
		// Get data
		List<Float> stressValues = new ArrayList<Float>(); // {5, 8, 9, 2, 5};
		List<Long> timeValues = new ArrayList<Long>();
		// {
		// 978307200, // 2001
		// 1009843200, // 2002
		// 1041379200, // 2003
		// 1072915200, // 2004
		// 1104537600 // 2005
		// };
		LocalDatabaseHandler db = new LocalDatabaseHandler(this);
		String dataToPrint = "";
		// List<CallData> periodData = db.getAllCallData();
		List<CallData> periodData = null;
		periodData = db.getPerPeriodCallDataInPeriod(unit, FromDate, ToDate);
		if (periodData.size() == 0) {
			return;
		}
		for (int i = 0; i < periodData.size(); i++) {
			CallData tempCallData = periodData.get(i);
			dataToPrint += tempCallData.ToString();
			stressValues.add(new Float(tempCallData.getRmsMedion()));
			try {
				timeValues.add(new Long(dateFormat.parse(tempCallData.getTimestamp()).getTime()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		;
		db.close();

		// create our series from our array of nums:
		XYSeries series2 = new SimpleXYSeries(timeValues, stressValues, unit);

		mySimpleXYPlot.addSeries(series2, BarRenderer.class, new BarFormatter(Color.argb(100, 0, 200, 0), Color.rgb(0, 80, 0)));
		mySimpleXYPlot.setDomainStepValue(timeValues.size());
		mySimpleXYPlot.setRangeStepValue(10);
		mySimpleXYPlot.setTicksPerRangeLabel(1);

		// per the android documentation, the minimum and maximum readings we
		// can get from
		// any of the orientation sensors is -180 and 359 respectively so we
		// will fix our plot's
		// boundaries to those values. If we did not do this, the plot would
		// auto-range which
		// can be visually confusing in the case of dynamic plots.
		mySimpleXYPlot.setRangeBoundaries(0, 10, BoundaryMode.FIXED);

		// use our custom domain value formatter:
		mySimpleXYPlot.setDomainValueFormat(new SimpleDateFormat("dd/MM/yy"));
//		mySimpleXYPlot.setDomainValueFormat(new Format() {
//			// create a simple date format that draws on the year portion of our
//			// timestamp.
//			// see
//			// http://download.oracle.com/javase/1.4.2/docs/api/java/text/SimpleDateFormat.html
//			// for a full description of SimpleDateFormat.
//			private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");
//
//			@Override
//			public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
//
//				// because our timestamps are in seconds and SimpleDateFormat
//				// expects milliseconds
//				// we multiply our timestamp by 1000:
//				long timestamp = ((Number) obj).longValue() * 1000;
//				Date date = new Date(timestamp);
//				return dateFormat.format(date, toAppendTo, pos);
//			}
//
//			@Override
//			public Object parseObject(String source, ParsePosition pos) {
//				return null;
//
//			}
//		});

		// update our domain and range axis labels:
		mySimpleXYPlot.setDomainLabel("Axis");
		mySimpleXYPlot.getDomainLabelWidget().pack();
		mySimpleXYPlot.setRangeLabel("Angle (Degs)");
		mySimpleXYPlot.getRangeLabelWidget().pack();

		mySimpleXYPlot.setGridPadding(15, 0, 15, 0);
		mySimpleXYPlot.disableAllMarkup();
		mySimpleXYPlot.redraw();
	}

	class DatePeriodSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			DatePeriod = parent.getItemAtPosition(pos).toString();
			if (DatePeriod.equals("Set from date")) {
				DialogFragment newFragment = new DatePickerFragment();
				newFragment.show(getSupportFragmentManager(), "timePicker");
			}else if (DatePeriod.equals("Set to date")) {
				DialogFragment newFragment = new DatePickerFragment();
				newFragment.show(getSupportFragmentManager(), "timePicker");
			}else if (DatePeriod.equals("Last week")) {
				setPeriodLastWeek();
			}else if (DatePeriod.equals("Last month")) {
				setPeriodLastMonth();
			}
		}

		public void onNothingSelected(AdapterView parent) {
			// Do nothing.
		}
	}

	class ChartUnitSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			ChartUnit = parent.getItemAtPosition(pos).toString();
		}

		public void onNothingSelected(AdapterView parent) {
			// Do nothing.
		}
	}

	@TargetApi(11)
	public class DatePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			int cYear = 2012;
			int cMonth = 10;
			int cDate = 11;
			if (DatePeriod.equals("Set from date")) {
				cYear = FromDate.getYear();
				cMonth = FromDate.getMonth();
				cDate = FromDate.getDate();				
				
			} else if (DatePeriod.equals("Set to date")){
				cYear = ToDate.getYear();
				cMonth = ToDate.getMonth();
				cDate = ToDate.getDate();		

			}
			cYear += 1900;
			// Create a new instance of TimePickerDialog and return it
			return new DatePickerDialog(context, new OnDateSetListener() {
				 @Override
				 public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						if (DatePeriod.equals("Set from date")) {
							 FromDate = new Date(year -1900, monthOfYear, dayOfMonth);		
							
						} else if (DatePeriod.equals("Set to date")){
							 ToDate = new Date(year -1900, monthOfYear, dayOfMonth);
						}
				 }}
				 , cYear, cMonth, cDate);
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// Do something with the time chosen by the user
		}

	}

}
