package edu.quantifiedself.callstressindicator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabaseHandler extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION  = 1;
	private static final String DATABASE_NAME = "call_analyzer_data";
	private static final String TABLE_DATA = "call_data";
	
	// columns name
	private static final String ID = "id";
	private static final String PHONE = "phone";
	private static final String RMS = "rms";
	private static final String DURATION = "duration";
	private static final String TIMESTAMP = "timestamp";
	private static final String ACCEL = "accel";

	public LocalDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_DATA + "("
                + ID + " INTEGER PRIMARY KEY," 
				+ PHONE + " TEXT,"
                + TIMESTAMP + " DATE,"
                + DURATION + " INTEGER,"
                + RMS + " REAL,"
                + ACCEL + " REAL"
                + ")";
		db.execSQL(CREATE_DATA_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        // Create tables again
        onCreate(db);
	}
	
	public void addCallData(CallData callData) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(PHONE, callData.getPhone());
	    values.put(TIMESTAMP,  callData.getTimestamp());
	    values.put(DURATION, callData.getDuration());
	    values.put(RMS, callData.getRmsMedion()); 
	    values.put(ACCEL, callData.getAccel()); 
	    // Inserting Row
	    db.insert(TABLE_DATA, null, values);
	    db.close(); // Closing database connection
	}

	public List<CallData> getCallData(String whereClause){
	    List<CallData> callDataList = new ArrayList<CallData>();
	    // Select All Query
	    String whereExpr = whereClause == "" ? "" : " WHERE " + whereClause;
	    String selectQuery = "SELECT * FROM " + TABLE_DATA + whereExpr;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	CallData callData = new CallData();
	            callData.setId(cursor.getInt(0));
	            callData.setPhone(cursor.getString(1));
				callData.setTimestamp( cursor.getString(2));
	            callData.setDuration(cursor.getInt(3));
	            callData.setRmsMedion(cursor.getDouble(4));
	            callData.setAccel(cursor.getFloat(5));

	            // Adding contact to list
	            callDataList.add(callData);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return callDataList;		
	}
	public String getTableSchema(){
	    List<CallData> callDataList = new ArrayList<CallData>();
	    String selectQuery = "PRAGMA table_info(" + TABLE_DATA + ")" ;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    String tableSchema = "";
	    if (cursor.moveToFirst()) {
	        do {
	            tableSchema += "name: " + cursor.getString(1) + ", type: " + cursor.getString(2) + "\n";
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return tableSchema;		
	}
	 
	// Getting All CallDatas
	public List<CallData> getAllCallData() {
	    return getCallData("");
  }
	 
	// Getting CallDatas in time period defined by start end (dates in unix epoch)
	public List<CallData> getCallDataInPeriod(Date from, Date to ) {
		from.setHours(0);
		from.setMinutes(0);
		from.setSeconds(0);
		
		to.setHours(23);
		to.setMinutes(59);
		to.setSeconds(59);
		
		String fromDate = MainActivity.dateFormat.format(from);
		String toDate = MainActivity.dateFormat.format(to);
	    return getCallData(TIMESTAMP + " between datetime('"+ fromDate +"') and datetime('" + toDate+"')");
  }
	 
	// Getting CallDatas Count
	public int getCallDataCount() {
		return 0;
	}
	// Updating single CallData
	public int updateCallData(CallData CallData) {
		return 0;
	}
	 
	// Deleting single CallData
	public void deleteCallData(CallData CallData) {}
	
	//insert training data in db
//	public void fillDb(){
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		addCallData(new CallData(5.65, 40000, 489894337, "+4915737119717", 3.44));
//		
//	}
}
