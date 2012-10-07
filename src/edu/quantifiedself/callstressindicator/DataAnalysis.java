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
		double total_score = 0;
		for(int i=0;i<N;i++){
			total_score += getScore(data.get(i), min, avg, max);
		}
		return (float)((10 * total_score)/N);
	}
	
	/*
	 * returns a score based on min, max, avg values
	 */
	public static double getScore(Double rmsValue, double min, double avg, double max){
		double group_2 = avg + avg*0.2; // 0.2
		double group_3 = avg + avg*0.4; // 0.4
		double group_4 = avg + avg*0.8; // 0.8
		double group_5 = avg + avg*1; // 1
		double group_6 = avg + avg*1.5; // 1.5 
		double group_7 = avg + avg*2; // 2
		double group_8 = avg + avg*2.5; // 2.5
		double group_9 = avg + avg*3; // 3
		double group_10 = avg + avg*3.5; // 3.5
		double group_11 = avg + avg*4; // 4
		double group_12 = avg + avg*4.5; // 4.5
		double group_13 = avg + avg*5; // 5
		double group_14 = avg + avg*5.5; // 5.5
		double group_15 = avg + avg*6; // 6
		double group_16 = avg + avg*6.5; // 6.5
		double group_17 = avg + avg*7; // 7
		double group_18 = avg + avg*8; // 7.5
		double group_19 = avg + avg*9; // 8
		double group_20 = avg + avg*10; // 8.5
		double group_21 = avg + avg*11; // 9
		double group_22 = avg + avg*12; // 9.5
		double group_23 = avg + avg*13; // 9
		double group_24 = avg + avg*15; // 8
		if(avg > rmsValue)
			return 0.0;
		if(avg < rmsValue && rmsValue < group_2)
			return 0.2;
		if(group_2 < rmsValue && rmsValue < group_3)
			return 0.1;
		if(group_3 < rmsValue && rmsValue < group_4)
			return 0.5;
		if(group_4 < rmsValue && rmsValue < group_5)
			return 1.5;
		if(group_5 < rmsValue && rmsValue < group_6)
			return 1.6;
		if(group_6 < rmsValue && rmsValue < group_7)
			return 1.7;
		if(group_7 < rmsValue && rmsValue < group_8)
			return 1.8;
		if(group_8 < rmsValue && rmsValue < group_9)
			return 1.9;
		if(group_9 < rmsValue && rmsValue < group_10)
			return 2;
		if(group_10 < rmsValue && rmsValue < group_11)
			return 2.1;
		if(group_11 < rmsValue && rmsValue < group_12)
			return 2.2;
		if(group_12 < rmsValue && rmsValue < group_13)
			return 2.4;
		if(group_13 < rmsValue && rmsValue < group_14)
			return 2.5;
		if(group_14 < rmsValue && rmsValue < group_15)
			return 2.6;
		if(group_15 < rmsValue && rmsValue < group_16)
			return 2.7;
		if(group_16 < rmsValue && rmsValue < group_17)
			return 2.8;
		if(group_17 < rmsValue && rmsValue < group_18)
			return 2.9;
		if(group_18 < rmsValue && rmsValue < group_19)
			return 3;
		if(group_19 < rmsValue && rmsValue < group_20)
			return 3.1;
		if(group_20 < rmsValue && rmsValue < group_21)
			return 3.2;
		if(group_21 < rmsValue && rmsValue < group_22)
			return 3.3;
		if(group_23 < rmsValue && rmsValue < group_23)
			return 3.4;
		if(group_24 < rmsValue && rmsValue < group_24)
			return 3.5;
		return 4.0;
	}

	public static double [] minMaxAvg(ArrayList<Double> data){
		int size = data.size();
		double sum = 0;
		double min = 1000000.0;
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

