package edu.quantifiedself.callstressindicator;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.androidplot.Plot;
import com.androidplot.series.XYSeries;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYStepMode;

public class MainActivity extends Activity {

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

		// Button queryTestBtn = (Button)findViewById(R.id.queryTest);
		// Button queryAllBtn = (Button)findViewById(R.id.queryAllBtn);
		// DatePicker fromDatePicker =
		// (DatePicker)findViewById(R.id.fromDatePicker);
		// DatePicker toDatePicker =
		// (DatePicker)findViewById(R.id.toDatePicker);
		// Get schema
		// LocalDatabaseHandler db = new LocalDatabaseHandler(context);
		// String dataToPrint = db.getTableSchema();
		// db.close();
		// TextView chartData = (TextView)findViewById(R.id.chartData);
		// chartData.setText(dataToPrint);
		// end

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
		// LocalDatabaseHandler db = new LocalDatabaseHandler(this);
		// db.addCallData(new CallData(1.65, 40000, "2012-09-01 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(1.65, 40000, "2012-09-01 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(2.65, 40000, "2012-09-01 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(2.65, 40000, "2012-09-01 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(3.65, 40000, "2012-09-01 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(3.65, 40000, "2012-09-01 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(4.65, 40000, "2012-09-09 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(4.65, 40000, "2012-09-09 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(5.65, 40000, "2012-09-09 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(5.65, 40000, "2012-09-09 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(6.65, 40000, "2012-09-09 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(6.65, 40000, "2012-09-09 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(7.65, 40000, "2012-09-09 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(7.65, 40000, "2012-10-01 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(8.65, 40000, "2012-10-01 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(8.65, 40000, "2012-10-01 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(9.65, 40000, "2012-10-01 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(9.65, 40000, "2012-10-03 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(1.65, 40000, "2012-10-03 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(1.65, 40000, "2012-10-03 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(2.65, 40000, "2012-10-03 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(2.65, 40000, "2012-10-03 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(3.65, 40000, "2012-10-03 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(3.65, 40000, "2012-10-09 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(4.65, 40000, "2012-10-09 18:20:00",
		// "+4915737119717", 3.44));
		// db.addCallData(new CallData(4.65, 40000, "2012-10-09 18:20:00",
		// "+4915737119717", 3.44));
		// db.close();

		Calendar c = Calendar.getInstance();
		final int cyear = c.get(Calendar.YEAR);
		final int cmonth = c.get(Calendar.MONTH);
		final int cday = c.get(Calendar.DAY_OF_MONTH);

		// seems weird but ...
		// FromDate = Calendar.getInstance().getTime();
		// ToDate = Calendar.getInstance().getTime();
		// monthofYear is between 0-11
		// fromDatePicker.init(cyear, cmonth, cday, new OnDateChangedListener()
		// {
		// @Override
		// public void onDateChanged(DatePicker view, int year, int monthOfYear,
		// int dayOfMonth) {
		// FromDate = new Date(year-1900, monthOfYear, dayOfMonth);
		// TextView chartData = (TextView)findViewById(R.id.chartData);
		// chartData.setText("fromDate: "
		// +CallData.dateFormat.format(FromDate));
		// }
		// });
		//
		// toDatePicker.init(cyear, cmonth, cday, new OnDateChangedListener() {
		// @Override
		// public void onDateChanged(DatePicker view, int year, int monthOfYear,
		// int dayOfMonth) {
		// ToDate = new Date(year-1900, monthOfYear, dayOfMonth);
		// TextView chartData = (TextView)findViewById(R.id.chartData);
		// chartData.setText("toDate: " +CallData.dateFormat.format(ToDate));
		// }
		// });
		//
		// queryTestBtn.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// LocalDatabaseHandler db = new LocalDatabaseHandler(context);
		// String dataToPrint = "";
		// List<CallData> savedData = db.getCallDataInPeriod(FromDate, ToDate);
		// // List<CallData> savedData =
		// db.getCallData("timstamp = 1349374389713");
		// for (int i = 0; i < savedData.size(); i++) {
		// CallData tempCallData = savedData.get(i);
		// dataToPrint += tempCallData.ToString();
		// } ;
		// db.close();
		// Log.i("savedData", dataToPrint);
		//
		// TextView chartData = (TextView)findViewById(R.id.chartData);
		// chartData.setText(dataToPrint);
		// }
		// });
		// queryAllBtn.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// LocalDatabaseHandler db = new LocalDatabaseHandler(context);
		// String dataToPrint = "";
		// List<CallData> savedData = db.getAllCallData();
		// for (int i = 0; i < savedData.size(); i++) {
		// CallData tempCallData = savedData.get(i);
		// dataToPrint += tempCallData.ToString();
		// } ;
		// db.close();
		// Log.i("savedData", dataToPrint);
		//
		// TextView chartData = (TextView)findViewById(R.id.chartData);
		// chartData.setText(dataToPrint);
		// }
		// });

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
		if (unit == "Per day") {
			periodData = db.getPerPeriodCallDataInPeriod("day", FromDate, ToDate);
		} else if (unit == "Per month") {
			periodData = db.getPerPeriodCallDataInPeriod("month", FromDate, ToDate);
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
		mySimpleXYPlot.setDomainValueFormat(new Format() {
			// create a simple date format that draws on the year portion of our
			// timestamp.
			// see
			// http://download.oracle.com/javase/1.4.2/docs/api/java/text/SimpleDateFormat.html
			// for a full description of SimpleDateFormat.
			private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");

			@Override
			public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {

				// because our timestamps are in seconds and SimpleDateFormat
				// expects milliseconds
				// we multiply our timestamp by 1000:
				long timestamp = ((Number) obj).longValue() * 1000;
				Date date = new Date(timestamp);
				return dateFormat.format(date, toAppendTo, pos);
			}

			@Override
			public Object parseObject(String source, ParsePosition pos) {
				return null;

			}
		});

		// update our domain and range axis labels:
		mySimpleXYPlot.setDomainLabel("Axis");
		mySimpleXYPlot.getDomainLabelWidget().pack();
		mySimpleXYPlot.setRangeLabel("Angle (Degs)");
		mySimpleXYPlot.getRangeLabelWidget().pack();

		mySimpleXYPlot.setGridPadding(15, 0, 15, 0);
		mySimpleXYPlot.disableAllMarkup();
		// mySimpleXYPlot.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);
		// mySimpleXYPlot.getGraphWidget().getGridLinePaint().setColor(Color.BLACK);
		// mySimpleXYPlot.getGraphWidget().getGridLinePaint().setPathEffect(new
		// DashPathEffect(new float[] { 1, 1 }, 1));
		// mySimpleXYPlot.getGraphWidget().getDomainOriginLinePaint().setColor(Color.BLACK);
		// mySimpleXYPlot.getGraphWidget().getRangeOriginLinePaint().setColor(Color.BLACK);
		//
		// mySimpleXYPlot.setBorderStyle(Plot.BorderStyle.SQUARE, null, null);
		// mySimpleXYPlot.getBorderPaint().setStrokeWidth(1);
		// mySimpleXYPlot.getBorderPaint().setAntiAlias(false);
		// mySimpleXYPlot.getBorderPaint().setColor(Color.WHITE);
		//
		// // Create a formatter to use for drawing a series using
		// // LineAndPointRenderer:
		// LineAndPointFormatter series1Format = new
		// LineAndPointFormatter(Color.rgb(0, 100, 0), // line
		// // color
		// Color.rgb(0, 100, 0), // point color
		// Color.rgb(100, 200, 0)); // fill color
		//
		// // setup our line fill paint to be a slightly transparent gradient:
		// Paint lineFill = new Paint();
		// lineFill.setAlpha(200);
		// lineFill.setShader(new LinearGradient(0, 0, 0, 250, Color.WHITE,
		// Color.GREEN, Shader.TileMode.MIRROR));
		//
		// LineAndPointFormatter formatter = new
		// LineAndPointFormatter(Color.rgb(0, 0, 0), Color.BLUE, Color.RED);
		// formatter.setFillPaint(lineFill);
		// mySimpleXYPlot.getGraphWidget().setPaddingRight(2);
		// mySimpleXYPlot.addSeries(series2, formatter);
		//
		// // draw a domain tick for each year:
		// mySimpleXYPlot.setDomainStep(XYStepMode.SUBDIVIDE,
		// timeValues.size());
		//
		// // customize our domain/range labels
		// mySimpleXYPlot.setDomainLabel("Date");
		// mySimpleXYPlot.setRangeLabel("Score value");
		//
		// // get rid of decimal points in our range labels:
		// mySimpleXYPlot.setRangeValueFormat(new DecimalFormat("0.#"));
		// mySimpleXYPlot.setDomainValueFormat(new Format() {
		//
		// // create a simple date format that draws on the year portion of our
		// // timestamp.
		// // see
		// //
		// http://download.oracle.com/javase/1.4.2/docs/api/java/text/SimpleDateFormat.html
		// // for a full description of SimpleDateFormat.
		// private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");
		// @Override
		// public StringBuffer format(Object obj, StringBuffer toAppendTo,
		// FieldPosition pos) {
		//
		// // because our timestamps are in seconds and SimpleDateFormat
		// // expects milliseconds
		// // we multiply our timestamp by 1000:
		// long timestamp = ((Number) obj).longValue() * 1000;
		// Date date = new Date(timestamp);
		// return dateFormat.format(date, toAppendTo, pos);
		// }
		//
		// @Override
		// public Object parseObject(String source, ParsePosition pos) {
		// return null;
		//
		// }
		// });
		//
		// // by default, AndroidPlot displays developer guides to aid in laying
		// // out your plot.
		// // To get rid of them call disableAllMarkup():
		// mySimpleXYPlot.disableAllMarkup();
	}

	class DatePeriodSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			DatePeriod = parent.getItemAtPosition(pos).toString();
			if (DatePeriod == "custom") {
//			    DialogFragment newFragment = new TimePickerFragment();
//			    newFragment.show(getSupportFragmentManager(), "timePicker");
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
	public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(context, this, hour, minute, DateFormat.is24HourFormat(context));
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// Do something with the time chosen by the user
		}
	}


}
