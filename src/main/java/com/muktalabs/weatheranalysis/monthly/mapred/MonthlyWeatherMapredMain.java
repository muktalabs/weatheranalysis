package com.muktalabs.weatheranalysis.monthly.mapred;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;

import com.muktalabs.weatheranalysis.daily.DailyWeatherHbaseOperations;

public class MonthlyWeatherMapredMain {
	public static void main(String args[]) {

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
