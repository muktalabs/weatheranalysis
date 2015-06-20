package com.muktalabs.weatheranalysis.daily.mapred;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.muktalabs.weatheranalysis.daily.DailyWeather;
import com.muktalabs.weatheranalysis.daily.DailyWeatherHbaseOperations;

import java.util.List;

public class DailyWeatherMapredMain {
	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "WeatherMapReduceJob");

		job.setJarByClass(DailyWeatherMapredMain.class);
		job.setMapperClass(DailyWeatherMapper.class);
		job.setCombinerClass(DailyWeatherReducer.class);
		job.setReducerClass(DailyWeatherReducer.class);
		job.setNumReduceTasks(1);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.addInputPath(job, new Path(
				"/mnt/data/workspace/weatheranalysis/data/2015"));
		Path out = new Path("/mnt/data/workspace/weatheranalysis/mapred/daily");
		File outDir = new File(out.toString());
		FileUtil.fullyDelete(outDir);
		FileOutputFormat.setOutputPath(job, out);

		DailyWeatherHbaseOperations.useTable();

		job.waitForCompletion(true);
		System.out.println("Job Completed.");

		testRecords();
	}

	private static void testRecords() throws IOException {
		Iterator<DailyWeather> dataIterator = DailyWeatherHbaseOperations
				.getAllRecords();
		while (dataIterator.hasNext()) {

			DailyWeather x = dataIterator.next();
			System.out.print(x.getStationCode());
			System.out.print(x.getYear());
			System.out.print(x.getMonth());
			System.out.println(x.getDay());

		}
		int count = 0;
		List<DailyWeather> list = new ArrayList<DailyWeather>();
		list = DailyWeatherHbaseOperations.get(420270, 2015, 2);
		for (DailyWeather d : list) {
			System.out.print(d.getStationCode() + " ");
			System.out.print(d.getYear() + " ");
			System.out.println(d.getMonth() + " ");
			System.out.print(d.getDay());
			System.out.println(d.getMaxTemp() + " ");
			count++;
		}
		System.out.println(count);
		DailyWeather d1 = DailyWeatherHbaseOperations.get(420270, 2015, 2, 3);
		System.out.print(d1.getStationCode() + " ");
		System.out.print(d1.getYear() + " ");
		System.out.print(d1.getMonth() + " ");
		System.out.print(d1.getMaxTemp() + " ");
	}
}