package edu.quantifiedself.callstressindicator;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.androidplot.Plot;
import com.androidplot.series.XYSeries;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_main);
		final Context context = this;
//		Button queryTestBtn = (Button)findViewById(R.id.queryTest);
//		Button queryAllBtn = (Button)findViewById(R.id.queryAllBtn);
//		DatePicker fromDatePicker = (DatePicker)findViewById(R.id.fromDatePicker);
//		DatePicker toDatePicker = (DatePicker)findViewById(R.id.toDatePicker);
		// Get schema
//        LocalDatabaseHandler db = new LocalDatabaseHandler(context);
//        String dataToPrint = db.getTableSchema();
//        db.close();
//		TextView chartData = (TextView)findViewById(R.id.chartData);
//		chartData.setText(dataToPrint);
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
		 // Initialize chart  
		createChart(FromDate, ToDate, ChartUnit );
        
        
		Calendar c = Calendar.getInstance();
		final int cyear = c.get(Calendar.YEAR);
		final int cmonth = c.get(Calendar.MONTH);
		final int cday = c.get(Calendar.DAY_OF_MONTH);
		
		// seems weird but ...
//		FromDate = Calendar.getInstance().getTime();
//		ToDate =  Calendar.getInstance().getTime();
		// monthofYear is between 0-11
//		fromDatePicker.init(cyear, cmonth, cday, new OnDateChangedListener() {
//			@Override
//			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//				FromDate = new Date(year-1900, monthOfYear, dayOfMonth);
//				TextView chartData = (TextView)findViewById(R.id.chartData);
//				chartData.setText("fromDate: " +CallData.dateFormat.format(FromDate));
//			}
//		});
//
//		toDatePicker.init(cyear, cmonth, cday, new OnDateChangedListener() {
//			@Override
//			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//				ToDate = new Date(year-1900, monthOfYear, dayOfMonth);
//				TextView chartData = (TextView)findViewById(R.id.chartData);
//				chartData.setText("toDate: " +CallData.dateFormat.format(ToDate));
//			}
//		});
//		
//		queryTestBtn.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//		        LocalDatabaseHandler db = new LocalDatabaseHandler(context);
//		        String dataToPrint = "";
//		        List<CallData> savedData =  db.getCallDataInPeriod(FromDate, ToDate);
////		        List<CallData> savedData =  db.getCallData("timstamp = 1349374389713");
//		        for (int i = 0; i < savedData.size(); i++) {
//		        	CallData tempCallData = savedData.get(i); 
//		        	dataToPrint += tempCallData.ToString();
//				} ;
//		        db.close();
//				Log.i("savedData", dataToPrint);
//				
//				TextView chartData = (TextView)findViewById(R.id.chartData);
//				chartData.setText(dataToPrint);
//			}
//		});
//		queryAllBtn.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//		        LocalDatabaseHandler db = new LocalDatabaseHandler(context);
//		        String dataToPrint = "";
//		        List<CallData> savedData =  db.getAllCallData();
//		        for (int i = 0; i < savedData.size(); i++) {
//		        	CallData tempCallData = savedData.get(i); 
//		        	dataToPrint += tempCallData.ToString();
//				} ;
//		        db.close();
//				Log.i("savedData", dataToPrint);
//				
//				TextView chartData = (TextView)findViewById(R.id.chartData);
//				chartData.setText(dataToPrint);
//			}
//		});

	}
	private void setPeriodLastWeek(){
		FromDate = Calendar.getInstance().getTime();
		FromDate.setDate(Calendar.getInstance().getTime().getDate() - 7);
		ToDate =  Calendar.getInstance().getTime();
		DatePeriod = "Last week";
	}
	private void setPeriodLastMonth(){
		FromDate = Calendar.getInstance().getTime();
		FromDate.setMonth(Calendar.getInstance().getTime().getMonth() - 1);
		ToDate =  Calendar.getInstance().getTime();
		DatePeriod = "Last month";
	}
	
	private void createChart(Date from, Date to, String unit) {
		// initialize our XYPlot reference:
        mySimpleXYPlot = (XYPlot) findViewById(R.id.startXYPlot);
        // Get data
        Number[] stressValues = {5, 8, 9, 2, 5};
        Number[] timeValues = {
                978307200,  // 2001
                1009843200, // 2002
                1041379200, // 2003
                1072915200, // 2004
                1104537600  // 2005
        };
        LocalDatabaseHandler db = new LocalDatabaseHandler(this);
        String dataToPrint = "";
        List<CallData> periodData =  db.getCallDataInPeriod(FromDate, ToDate);
        for (int i = 0; i < periodData.size(); i++) {
        	CallData tempCallData = periodData.get(i); 
        	dataToPrint += tempCallData.ToString();
        	switch (Enums.ChartUnits.valueOf(Enums.ChartUnits, unit)) {
			case Enums.ChartUnits.Per_day:
				
				break;

			default:
				break;
			}
        	
		} ;
        db.close();
        
        
        // create our series from our array of nums:
        XYSeries series2 = new SimpleXYSeries(
                Arrays.asList(stressValues),
                Arrays.asList(timeValues),
                unit);
 
        mySimpleXYPlot.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);
        mySimpleXYPlot.getGraphWidget().getGridLinePaint().setColor(Color.BLACK);
        mySimpleXYPlot.getGraphWidget().getGridLinePaint().setPathEffect(new DashPathEffect(new float[]{1,1}, 1));
        mySimpleXYPlot.getGraphWidget().getDomainOriginLinePaint().setColor(Color.BLACK);
        mySimpleXYPlot.getGraphWidget().getRangeOriginLinePaint().setColor(Color.BLACK);
 
        mySimpleXYPlot.setBorderStyle(Plot.BorderStyle.SQUARE, null, null);
        mySimpleXYPlot.getBorderPaint().setStrokeWidth(1);
        mySimpleXYPlot.getBorderPaint().setAntiAlias(false);
        mySimpleXYPlot.getBorderPaint().setColor(Color.WHITE);
 
        // Create a formatter to use for drawing a series using LineAndPointRenderer:
        LineAndPointFormatter series1Format = new LineAndPointFormatter(
                Color.rgb(0, 100, 0),                   // line color
                Color.rgb(0, 100, 0),                   // point color
                Color.rgb(100, 200, 0));                // fill color
 
 
        // setup our line fill paint to be a slightly transparent gradient:
        Paint lineFill = new Paint();
        lineFill.setAlpha(200);
        lineFill.setShader(new LinearGradient(0, 0, 0, 250, Color.WHITE, Color.GREEN, Shader.TileMode.MIRROR));
 
        LineAndPointFormatter formatter  = new LineAndPointFormatter(Color.rgb(0, 0,0), Color.BLUE, Color.RED);
        formatter.setFillPaint(lineFill);
        mySimpleXYPlot.getGraphWidget().setPaddingRight(2);
        mySimpleXYPlot.addSeries(series2, formatter);
 
        // draw a domain tick for each year:
        mySimpleXYPlot.setDomainStep(XYStepMode.SUBDIVIDE, years.length);
 
        // customize our domain/range labels
        mySimpleXYPlot.setDomainLabel("Year");
        mySimpleXYPlot.setRangeLabel("# of Sightings");
 
        // get rid of decimal points in our range labels:
        mySimpleXYPlot.setRangeValueFormat(new DecimalFormat("0"));
 
        mySimpleXYPlot.setDomainValueFormat(new Format() {
 
            // create a simple date format that draws on the year portion of our timestamp.
            // see http://download.oracle.com/javase/1.4.2/docs/api/java/text/SimpleDateFormat.html
            // for a full description of SimpleDateFormat.
            private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
 
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
 
                // because our timestamps are in seconds and SimpleDateFormat expects milliseconds
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
 
        // by default, AndroidPlot displays developer guides to aid in laying out your plot.
        // To get rid of them call disableAllMarkup():
        mySimpleXYPlot.disableAllMarkup();
	}
	
	class DatePeriodSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			DatePeriod = parent.getItemAtPosition(pos).toString();				
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
	
}
