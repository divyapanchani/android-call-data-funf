package edu.quantifiedself.callstressindicator;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
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
	private static final String TIMESTAMP = "timstamp";
	private static final String ACCEL = "accel";

	public LocalDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_DATA + "("
                + ID + " INTEGER PRIMARY KEY," 
				+ PHONE + " TEXT,"
                + TIMESTAMP + " INTEGER,"
                + DURATION + " REAL,"
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
	    values.put(TIMESTAMP, callData.getTimestamp());
	    values.put(RMS, callData.getTimestamp()); 
	}
	 
	// Getting single CallData
	public CallData getCallData(int id) {}
	 
	// Getting All CallDatas
	public List<CallData> getAllCallData() {}
	 
	// Getting CallDatas Count
	public int getCallDataCount() {}
	// Updating single CallData
	public int updateCallData(CallData CallData) {}
	 
	// Deleting single CallData
	public void deleteCallData(CallData CallData) {}

}
