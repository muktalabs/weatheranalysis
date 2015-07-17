package com.muktalabs.weatheranalysis.monthly.mapred;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.muktalabs.weatheranalysis.daily.mapred.WeatherCalculations;
import com.muktalabs.weatheranalysis.monthly.MonthlyWeather;
import com.muktalabs.weatheranalysis.monthly.MonthlyWeatherHbaseOperations;

public class MonthlyWeatherReducer extends Reducer<Text, Text, IntWritable, Text> {

	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {

        MonthlyWeather monthly=new MonthlyWeather();
       
        int yr=0, mn=0;
		String w = key.toString();
		String[] fArr = w.split(",");
		int stationcode = Integer.parseInt(fArr[0]);
		yr = Integer.parseInt(fArr[1]);
		mn = Integer.parseInt(fArr[2]);
		
		monthly.setStationCode(stationcode);
		monthly.setYear(yr);
		monthly.setMonth(mn);
		
        
        
        
        System.out.println("Monthly Reducer: key=" + key.toString());
		try {
			List<Float> l1=new ArrayList<Float>();
			
		l1=WeatherCalculations.process(key, values);
		monthly.setMinTemp(l1.get(0));
		monthly.setMaxTemp(l1.get(1));
	    monthly.setAvgTemp(l1.get(2));
	    monthly.setDewpoint(l1.get(3));
	    monthly.setPrecipitation(l1.get(4));
	    
	    System.out.println("Reducer: saving " + monthly);
		MonthlyWeatherHbaseOperations.addRecord(monthly);
		} catch (Exception e) {
			System.out.println("Error in reducer: ");
			e.printStackTrace();
		}

		//context.write(new IntWritable(1), new Text(monthly.toString()));
	}

	//String keytxt = st + "," + y1 + "," + m1;
	//String valuetxt = min + "," + max + "," + avg + "," + dewpt + "," + ppt;
	//System.out.println("Key="+key.toString());
	}




