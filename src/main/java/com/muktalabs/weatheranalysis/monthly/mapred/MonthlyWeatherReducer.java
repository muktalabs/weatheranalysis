package com.muktalabs.weatheranalysis.monthly.mapred;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class MonthlyWeatherReducer extends Reducer<Text, Text, IntWritable, Text> {

	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {


		System.out.println("Monthly Reducer: key=" + key.toString());
		try {
			process(key, values);
		} catch (Exception e) {
			System.out.println("Error in reducer: ");
			e.printStackTrace();
		}

		//context.write(new IntWritable(1), new Text(dailyweather.toString()));
	}

	//String keytxt = st + "," + y1 + "," + m1;
	//String valuetxt = min + "," + max + "," + avg + "," + dewpt + "," + ppt;
	private void process(Text key, Iterable<Text> values) {

		System.out.println("Key="+key.toString());
	}

}
