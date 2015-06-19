package com.muktalabs.weatheranalysis.daily.mapred;

import java.io.IOException;
import java.util.StringTokenizer;

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

		System.out.println("Reducer: key=" + key.toString());
		try {
			process(key, values);
		} catch (Exception e) {
			System.out.println("Error in reducer: ");
			e.printStackTrace();
		}

		//context.write(new IntWritable(1), new Text(dailyweather.toString()));
	}

	private void process(Text key, Iterable<Text> values) throws Exception {
		DailyWeather dailyweather = new DailyWeather();

		int yr=0, mn=0, dy=0;
		String w = key.toString();
		String[] fArr = w.split(",");
		int stationcode = Integer.parseInt(fArr[0]);
		yr = Integer.parseInt(fArr[1]);
		mn = Integer.parseInt(fArr[2]);
		dy = Integer.parseInt(fArr[3]);
		
		dailyweather.setStationCode(stationcode);
		dailyweather.setYear(yr);
		dailyweather.setMonth(mn);
		dailyweather.setDay(dy);

		byte sum = 0;
		float sumTemp = 0, counter = 0, sumDew = 0, sumPpt = 0, maxTemp = 0, minTemp = 1000;
		float avgTemp, TotalPpt, avgDew;
		for (Text val : values) {
			String g = val.toString();
			StringTokenizer daily = new StringTokenizer(g, ",");
			int r = 0;
			while (daily.hasMoreTokens()) {
				float parse = Float.parseFloat(daily.nextToken());
				if (r == 0) {
					float localTemp = parse;
					
					if (localTemp > maxTemp) {
						maxTemp = localTemp;
					}
					if (localTemp < minTemp) {
						minTemp = localTemp;
					}
					sumTemp = sumTemp + parse;
				}
				if (r == 1) {
					sumDew = sumDew + parse;
				}
				if (r == 2) {
					sumPpt = parse;
				}
				r++;
			}
			counter++;
		}
		avgTemp = sumTemp / counter;
		avgDew = sumDew / counter;
		TotalPpt = sumPpt;

		dailyweather.setMinTemp(minTemp);
		dailyweather.setMaxTemp(maxTemp);
		dailyweather.setAvgTemp(avgTemp);
		dailyweather.setPrecipitation(TotalPpt);
		dailyweather.setDewpoint(avgDew);

		System.out.println("Reducer: saving " + dailyweather);
		DailyWeatherHbaseOperations.addRecord(dailyweather);
	}

}
