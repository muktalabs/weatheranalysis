package com.muktalabs.weatheranalysis.daily.mapred;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.muktalabs.weatheranalysis.parser.ISHParser;

public class DailyWeatherMapper extends Mapper<Object, Text, Text, Text> {

	final SimpleDateFormat sdformatter = new SimpleDateFormat("yyyyMMddHHmm");
	
	public void map(Object key, Text line, Context context) throws IOException,
			InterruptedException {

		// The value is 1 line of the input file.
		String parsed = ISHParser.parse(line.toString());
		//System.out.println("Mapper: parsed: "+parsed);
		
		
		String yearmonthday = null;
		int i = 0;

		String station1 = null, yy = null, mm = null, dd = null, temp = null, dewpt = null, ppt = null;
		Scanner sc = new Scanner(parsed);
		while (sc.hasNext()) {
			if (i == 0) {
				station1 = sc.next();
			} else if (i == 2) {
				yearmonthday = sc.next();
			} else if (i == 21) {
				temp = sc.next();
			if(temp.contains("*")){
				temp="5555";
			}
			}else if (i == 22) {
				dewpt = sc.next();
				if(dewpt.contains("*")){
							dewpt="5555";
						}	
			
			} else if (i == 31) {
				ppt = sc.next();
				if(ppt.contains("*")){
					ppt="5555";
				}		
					} else {
				sc.next();
			}

			i++;
		}
		sc.close();
		
		// Converting From GMT to IST
		try {
			String oldDate = yearmonthday;
			sdformatter.setTimeZone(TimeZone.getTimeZone("GMT"));
			Date timezone = sdformatter.parse(yearmonthday);
			sdformatter.setTimeZone(TimeZone.getTimeZone("IST"));
			yearmonthday = sdformatter.format(timezone);
			System.out.println("Mapper: old date="+oldDate+", new date="+yearmonthday);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		yy = yearmonthday.substring(0, 4);
		mm = yearmonthday.substring(4, 6);
		dd = yearmonthday.substring(6, 8);
		int st = Integer.parseInt(station1);
		int y1 = Integer.parseInt(yy);
		int m1 = Integer.parseInt(mm);
		int d1 = Integer.parseInt(dd);
		
		String keytxt = st + "," + y1 + "," + m1 + "," + d1;

		String value = temp + "," + dewpt + "," + ppt;

		System.out.println("Mapper: key="+keytxt+", value="+value);
		context.write(new Text(keytxt), new Text(value));

	}
}
