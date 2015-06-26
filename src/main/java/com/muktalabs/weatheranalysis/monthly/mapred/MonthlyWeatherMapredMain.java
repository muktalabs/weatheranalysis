package com.muktalabs.weatheranalysis.monthly.mapred;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.muktalabs.weatheranalysis.daily.DailyWeatherHbaseOperations;
import com.muktalabs.weatheranalysis.monthly.MonthlyWeather;
import com.muktalabs.weatheranalysis.monthly.MonthlyWeatherHbaseOperations;

public class MonthlyWeatherMapredMain {
	public static void main(String args[]) throws Exception{

		try {

			Configuration config = HBaseConfiguration.create();
			config.set("mapred.output.dir",
					"/mnt/data/workspace/weatheranalysis/mapred/monthly");
			Job job = new Job(config, "MonthlySummary");

			String sourceTable = DailyWeatherHbaseOperations.TABLE_NAME;

			Scan scan = new Scan();
			scan.setCaching(500); // 1 is the default in Scan, which will be bad
									// for
									// MapReduce jobs
			scan.setCacheBlocks(false); // don't set to true for MR jobs
			// set other scan attrs

			TableMapReduceUtil.initTableMapperJob(sourceTable, // input table
					scan, // Scan instance to control CF and attribute selection
					MonthlyWeatherMapper.class, // mapper class
					Text.class, // mapper output key
					Text.class, // mapper output value
					job);

			job.setReducerClass(MonthlyWeatherReducer.class); // reducer class
			job.setNumReduceTasks(1); // at least one, adjust as required

			Path out = new Path("/mnt/data/workspace/weatheranalysis/mapred/monthly");
			File outDir = new File(out.toString());
			FileUtil.fullyDelete(outDir);
			FileOutputFormat.setOutputPath(job, out);

			MonthlyWeatherHbaseOperations.useTable();

			
			//The required Total Precipitate for a particular station for an year
			/* Manpreet Singh Tuteja*/
			  
		     /* List<Float> ForAllMonthsPpt = new ArrayList<Float>();
			  List<MonthlyWeather> lr = MonthlyWeatherHbaseOperations.get(425010,2013);
		        while(lr.isEmpty()) {  
			    ForAllMonthsPpt.add(lr.get(4));
		
		}
			 * 
			 * 
			 * 
			 * 
			 */
			job.waitForCompletion(true);
			System.out.println("Job Completed.");

			
			
			boolean b = job.waitForCompletion(true);
			if (!b) {
				throw new IOException("error with job!");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
