package edu.quantifiedself.callstressindicator;

public class Enums {

	public static enum ChartUnits {
//		Per_call("Per call"), Per_day("Per day"), Per_month("Per month");
		Per_call, Per_day, Per_month;

//		 private String code;
//		 
//		 private ChartUnits(String c) {
//		   code = c;
//		 }
//		 
//		 public String getCode() {
//		   return code;
//		 }
	}

	public static enum DatePeriods {
		last_week("Last week"), lastlmonth("Last month"), custom("custom");

		 private String code;
		 
		 private DatePeriods(String c) {
		   code = c;
		 }
		 
		 public String getCode() {
		   return code;
		 }
	}
}