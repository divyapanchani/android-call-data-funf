package edu.quantifiedself.callstressindicator;

import java.util.ArrayList;

public class DataAnalysis {
	
    public static double rms(double[] nums){
        double ms = 0;
        for (int i = 0; i < nums.length; i++)
            ms += nums[i] * nums[i];
        ms /= nums.length;
        return Math.sqrt(ms);
    }
	
	public static double updateValues(double newValue, double oldValue){
		return (0.6*oldValue + 0.4*newValue);
	}
	
	/*
	 * returns the loudness score for the call between 1 - 10
	 */
	public static float getScaledScore(ArrayList<Double> data, double min, double max, double avg){
		int N = data.size();
		int total_score = 0;
		for(int i=0;i<N;i++){
			total_score += getScore(data.get(i), min, avg, max);
		}
		return (float)((10 * total_score)/N);
	}
	
	/*
	 * returns a score based on min, max, avg values
	 */
	public static int getScore(Double rmsValue, double min, double avg, double max){
		double lower_bound = min + min*5/100;
		double upper_bound = max - max*5/100;
		double lower_avg = avg - avg*5/100;
		double upper_avg = avg + avg*5/100;
		if(lower_bound < rmsValue && rmsValue < lower_avg)
			return 0;
		if(lower_avg < rmsValue && rmsValue < upper_avg)
			return 0;
		if(upper_avg < rmsValue && rmsValue < upper_bound)
			return 1;
		return 0;
	}

	public static double [] minMaxAvg(ArrayList<Double> data){
		int size = data.size();
		double sum = 0;
		double min = 100.0;
		double max = -1.0;
		for(int i=0;i<size;i++){
			sum = sum + data.get(i);
			if(data.get(i) > max)
				max = data.get(i);
			else if(data.get(i) < min)
				min = data.get(i);
		}
		double [] to_return = new double[3];
		to_return[0] = min;
		to_return[1] = max;
		to_return[2] = sum/size;
		return to_return;
		
	}
}

