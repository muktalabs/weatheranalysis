package com.muktalabs.weatheranalysis.monthly.mapred;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.Text;

import com.muktalabs.weatheranalysis.daily.DailyWeather;
import com.muktalabs.weatheranalysis.daily.DailyWeatherHbaseOperations;

public class MonthlyWeatherMapper extends TableMapper<Text, Text> {

	public void map(ImmutableBytesWritable row, Result value, Context context)
	throws IOException, InterruptedException
	{
		int st=0,y1=0,m1=0;
		float min=0,max=0,avg=0,dewpt=0,ppt=0;
		DailyWeather daily = DailyWeatherHbaseOperations.parse(value);
		st=daily.getStationCode();
		y1=daily.getYear();
		m1=daily.getMonth();
		min=daily.getMinTemp();
		max=daily.getMaxTemp();
		avg=daily.getAvgTemp();
		dewpt=daily.getDewpoint();
		ppt=daily.getPrecipitation();
		String keytxt = st + "," + y1 + "," + m1;

		String valuetxt = min + "," + max + "," + avg + "," + dewpt + "," + ppt;
		
		System.out.println("Monthly Mapper: key="+keytxt+", value="+valuetxt);
		context.write(new Text(keytxt), new Text(valuetxt));
	}
}
