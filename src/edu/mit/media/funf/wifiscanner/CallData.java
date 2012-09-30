package edu.mit.media.funf.wifiscanner;

public class CallData {
	
	private double rmsMedion;
	private float duration;
	private long timestamp;
	private String phone;
	private float accel;
	
	public float getAccel() {
		return accel;
	}
	public void setAccel(float accel) {
		this.accel = accel;
	}
	public double getRmsMedion() {
		return rmsMedion;
	}
	public void setRmsMedion(double rmsMedion) {
		this.rmsMedion = rmsMedion;
	}
	public float getDuration() {
		return duration;
	}
	public void setDuration(float duration) {
		this.duration = duration;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	

}
