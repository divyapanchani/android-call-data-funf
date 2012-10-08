package edu.quantifiedself.callstressindicator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CallData {
	
	private int id;
	private double rmsMedion;
	private int duration;
	private String timestamp;
	private String phone;
	private double accel;	
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public CallData() {
	}
	
	public CallData(double rmsMedion, int duration, String timestamp, String phone, double accel) {
		this.rmsMedion = rmsMedion;
		this.duration = duration;
		this.timestamp = timestamp;
		this.phone = phone;
		this.accel = accel;
	}	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getAccel() {
		return accel;
	}
	public void setAccel(double accel) {
		this.accel = accel;
	}
	public double getRmsMedion() {
		return rmsMedion;
	}
	public void setRmsMedion(double rmsMedion) {
		this.rmsMedion = rmsMedion;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public void setTimestamp(Date date) {
		this.timestamp = this.dateFormat.format(date);
	}

	public String ToString(){
		String returnString = "";
		returnString += 
				"ID: " + this.getId() +"\n" +
				"TIMESTAMP: " + this.getTimestamp()+"\n" +
				"DURATION: " + this.getDuration() +"\n" +
				"PHONE: " + this.getPhone() +"\n" +
				"RMS: " + this.getRmsMedion() +"\n" +
				"ACCEL: " + this.getAccel() +"\n";
		return returnString;
		
	}

}
