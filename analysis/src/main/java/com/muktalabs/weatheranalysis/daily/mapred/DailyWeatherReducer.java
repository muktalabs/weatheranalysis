package com.muktalabs.weatheranalysis.daily.mapred;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.muktalabs.weatheranalysis.daily.DailyWeather;
import com.muktalabs.weatheranalysis.daily.DailyWeatherHbaseOperations;

public class DailyWeatherReducer extends Reducer<Text, Text, IntWritable, Text> {
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {

		// String keytxt = st + "|" + y1 + "|" + m1 + "|" + d1;
		// String value = temp + "|" + dewpt + "|" + ppt;

		DailyWeather daily = new DailyWeather();

		int yr = 0, mn = 0, day=0;
		String w = key.toString();
		String[] fArr = w.split(",");
		int stationcode = Integer.parseInt(fArr[0]);
		yr = Integer.parseInt(fArr[1]);
		mn = Integer.parseInt(fArr[2]);
		day = Integer.parseInt(fArr[3]);

		daily.setStationCode(stationcode);
		daily.setYear(yr);
		daily.setMonth(mn);
		daily.setDay(day);

		System.out.println("Daily Reducer: key=" + key.toString());
		try {
			List<Float> l1 = new ArrayList<Float>();

			l1 = WeatherCalculations.process1(key, values);
			daily.setMinTemp(l1.get(0));
			daily.setMaxTemp(l1.get(1));
			daily.setAvgTemp(l1.get(2));
			daily.setDewpoint(l1.get(3));
			daily.setPrecipitation(l1.get(4));

			System.out.println("Reducer: saving " + daily);
			DailyWeatherHbaseOperations.addRecord(daily);
		} catch (Exception e) {
			System.out.println("Error in reducer: ");
			e.printStackTrace();
		}

		// context.write(new IntWritable(1), new Text(monthly.toString()));
	}

	// String keytxt = st + "," + y1 + "," + m1;
	// String valuetxt = min + "," + max + "," + avg + "," + dewpt + "," + ppt;
	// System.out.println("Key="+key.toString());
}
