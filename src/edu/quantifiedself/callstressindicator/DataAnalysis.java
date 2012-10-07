package edu.quantifiedself.callstressindicator;

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
	public static int getScaledScore(double [] data, double min, double max, double avg){
		int N = data.length;
		int total_score = 0;
		for(int i=0;i<N;i++){
			total_score += getScore(data[i], min, avg, max);
		}
		return (10 * total_score) / N;
	}
	
	/*
	 * returns a score based on min, max, avg values
	 */
	public static int getScore(double rmsValue, double min, double avg, double max){
		double lower_bound = min +min*5/100;
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

	public static double [] minMaxAvg(double [] data){
		int size = data.length;
		double sum = 0;
		double min = 100.0;
		double max = -1.0;
		for(int i=0;i<size;i++){
			sum = sum + data[i];
			if(data[i] > max)
				max = data[i];
			else if(data[i] < min)
				min = data[i];
		}
		double [] to_return = new double[3];
		to_return[0] = min;
		to_return[1] = max;
		to_return[2] = sum/size;
		return to_return;
		
	}
}

