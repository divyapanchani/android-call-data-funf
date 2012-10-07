package edu.quantifiedself.callstressindicator;

import java.util.ArrayList;

public class DataAnalysis {
	
	public static double rmsAvg(double [] data){
	int size = data.length;
	double sum = 0;
	for(int i=0;i<size;i++)
		sum = sum + data[size];
	return sum / size;
	}

	public static ArrayList<Double> overThreshold(double [] data, double threshold){
		ArrayList<Double> a = new ArrayList<Double>();
		for(int i=0;i<data.length;i++){
			if(data[i] > threshold)
				a.add(data[i]);
		}
		return a;
	}
	
	public static double [] minMaxAvg(double [] data){
		int size = data.length;
		double sum = 0;
		double min = 100.0;
		double max = 100.0;
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

