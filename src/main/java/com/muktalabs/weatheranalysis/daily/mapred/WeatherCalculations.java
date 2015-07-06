package com.muktalabs.weatheranalysis.daily.mapred;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.hadoop.io.Text;

public class WeatherCalculations {

	public static List<Float> process(Text key, Iterable<Text> values) {
		float sumTemp = 0, counterdew = 0, countertemp = 0, sumDew = 0, sumPpt = 0, maxTemp = 0, minTemp = 1000, Ppt = 0;
		float avgTemp = 0, TotalPpt = 0, avgDew = 0;
		for (Text val : values) {
			String g = val.toString();
			StringTokenizer monthly = new StringTokenizer(g, ",");
			int r = 0;
			while (monthly.hasMoreTokens()) {
				float parse = Float.parseFloat(monthly.nextToken());
				if (parse == 5555 ) {
					r++;
				} else {
                       if(r==0){
						float localTemp1 = parse;

						if (localTemp1 < minTemp) {
							minTemp = localTemp1;
						}
					}

					if (r == 1) {
						float localTemp = parse;
						
						if (localTemp > maxTemp) {
							maxTemp = localTemp;
						}
					}
					if(r==2){
					
					sumTemp = sumTemp + parse;

						countertemp++;
					}
				
					if (r == 3) {
						System.out.println("dewpoint: " + parse);
						sumDew = sumDew + parse;
						counterdew++;
					}
					if (r == 4) {
						sumPpt = sumPpt + parse;
						System.out.println("precipitation: " + sumPpt);
					}
					r++;
				}
			}
		}
		avgTemp = sumTemp / (countertemp);
		avgDew = sumDew / (counterdew);
			TotalPpt = sumPpt;
		 

		List<Float> l = new ArrayList<Float>();
		l.add(minTemp);
		l.add(maxTemp);
		l.add(avgTemp);
		l.add(avgDew);
		l.add(TotalPpt);

		return l;

	}


	public static List<Float> process1(Text key, Iterable<Text> values) {
		float sumTemp = 0, counterdew = 0, countertemp = 0, sumDew = 0, maxTemp = 0, minTemp = 1000, Ppt = 0;
		float avgTemp = 0, TotalPpt = 0, avgDew = 0;
		for (Text val : values) {
			String g = val.toString();
			StringTokenizer monthly = new StringTokenizer(g, ",");
			int r = 0;
			while (monthly.hasMoreTokens()) {
				float parse = Float.parseFloat(monthly.nextToken());
				if (parse == 5555 ) {
					r++;
				} else {
                       if(r==0){
						float localTemp1 = parse;
						float localTemp = parse;
						
						if (localTemp1 < minTemp) {
							minTemp = localTemp1;
						}
						if (localTemp > maxTemp) {
							maxTemp = localTemp;
						}
					
					sumTemp = sumTemp + parse;

						countertemp++;
					}
				
					if (r == 1) {
						System.out.println("dewpoint: " + parse);
						sumDew = sumDew + parse;
						counterdew++;
					}
					if (r == 2) {
						Ppt = parse;
							System.out.println("precipitation: " + Ppt);
					}
					r++;
				}
			}
		}
		avgTemp = sumTemp / (countertemp);
		avgDew = sumDew / (counterdew);
			TotalPpt = Ppt;
		

		List<Float> l = new ArrayList<Float>();
		l.add(minTemp);
		l.add(maxTemp);
		l.add(avgTemp);
		l.add(avgDew);
		l.add(TotalPpt);

		return l;

	}
}