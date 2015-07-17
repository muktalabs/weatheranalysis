package com.muktalabs.weatheranalysis.monthly.mapred;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.muktalabs.weatheranalysis.monthly.MonthlyWeather;
import com.muktalabs.weatheranalysis.monthly.MonthlyWeatherHbaseOperations;

public class alphanot {
	static HashMap<String, Double> sl = new HashMap<String, Double>();
	static HashMap<String, Double> lk = new HashMap<String, Double>();
	static String stcode;
	static int yr;
	static double k[] = new double[12];
    static double sup = 0;
    static double out = 0;
	public static void AdjustmentFactor( ) throws IOException {
		// Put elements to the map
		lk.put("3001", new Double(0.87));
		lk.put("3002", new Double(0.93));
		lk.put("3003", new Double(1.00));
		lk.put("3004", new Double(1.07));
		lk.put("3005", new Double(1.14));
		lk.put("3006", new Double(1.17));
		lk.put("3007", new Double(1.16));
		lk.put("3008", new Double(1.11));
		lk.put("3009", new Double(1.03));
		lk.put("3010", new Double(0.96));
		lk.put("3011", new Double(0.89));
		lk.put("3012", new Double(0.85));

		lk.put("2001", new Double(0.92));
		lk.put("2002", new Double(0.96));
		lk.put("2003", new Double(1.00));
		lk.put("2004", new Double(1.05));
		lk.put("2005", new Double(1.09));
		lk.put("2006", new Double(1.11));
		lk.put("2007", new Double(1.10));
		lk.put("2008", new Double(1.07));
		lk.put("2009", new Double(1.02));
		lk.put("2010", new Double(0.98));
		lk.put("2011", new Double(0.93));
		lk.put("2012", new Double(0.91));

		lk.put("1001", new Double(0.97));
		lk.put("1002", new Double(0.98));
		lk.put("1003", new Double(1.00));
		lk.put("1004", new Double(1.03));
		lk.put("1005", new Double(1.05));
		lk.put("1006", new Double(1.06));
		lk.put("1007", new Double(1.05));
		lk.put("1008", new Double(1.04));
		lk.put("1009", new Double(1.02));
		lk.put("1010", new Double(0.99));
		lk.put("1011", new Double(0.97));
		lk.put("1012", new Double(0.96));

		try {
			 stcode = null;
					String latitude = null;
			FileReader fm = new FileReader(
					"/mnt/data/workspace/weatheranalysis/analysis/src/main/resources/station_lati.txt");
			BufferedReader br = new BufferedReader(fm);
			String line = null;
			while ((line = br.readLine()) != null) {
				int i = 0;
				Scanner sc = new Scanner(line);

				while (sc.hasNext()) {

					if (i==0) {
						stcode = sc.next();
						//System.out.println("Stationcode : " + stcode);
						}
					   else if (i == 4) {
						latitude = sc.next();
						//System.out.println("latitude : " + latitude);
					} else {
						sc.next();
					}
					i++;

				}
				sl.put(stcode, Double.parseDouble(latitude));
				sc.close();

			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
     /*yr = year;
     System.out.println("The station code that has been searched is "+stationcode);
     System.out.println("The year for which it was searched is "+yr);
     
	double out = getkvalues(stationcode, yr);
	System.out.println("The value of out is:" +out);*/
	}

	public static double getkvalues(String stationcode, int year) throws IOException {
		AdjustmentFactor( );
		stcode = stationcode;
		yr = year;
		System.out.println(" i am in getk and stcode is "+stcode);
		int i = 1;
		int latitudereq;
		try {
			double latitude = sl.get(stationcode);
			if((((int) latitude) % 10)<5){
			latitudereq = (((int) latitude) / 10) * 10;
			}
			else{
				latitudereq = ((((int) latitude) / 10)+1) * 10;
			}
            System.out.println("getk latitude is"+latitudereq);
			while (i <= 12) {
				int keyforHash = latitudereq * 100 + i;
				String key1 = "" + keyforHash;
				double kvalue = lk.get(key1);
				k[i - 1] = kvalue;
				System.out.println("constant : "+k[i - 1]);
				i++;
			}

			System.out.println("getk stcode again is "+stcode);
		  sup=calcu(stcode);
		 System.out.println("value of getk sup is "+sup);
		} catch (Exception w) {
			w.printStackTrace();
		}
		return sup;
	}

	public static double calcu(String stationcode) throws IOException {
		int st = Integer.parseInt(stationcode);
		int i = 0;
		double sumpet = 0;
		double pet1[] = new double[12];
		double I = 0, J = 0, C = 0, temp, pet;
		double temp1[] = new double[12];
		List<MonthlyWeather> l = new ArrayList<MonthlyWeather>();
		l = MonthlyWeatherHbaseOperations.get(st, yr);
		for (MonthlyWeather me : l) {
			temp = me.getAvgTemp();
				System.out.println("monthnumber :"+me.getMonth());
			System.out.println("monthly mean temperature :"+temp);
			I = Math.pow((temp / 5), 1.514);
			temp1[i] = temp;
			J = J + I;
			i++;
		}
		C = 0.000000675 * Math.pow(J, 3) - 0.0000771 * Math.pow(J, 2) + 0.01792
				* J + 0.49239;
		for (int j = 0; j < 12; j++) {
			pet = 1.6 * Math.pow(((10 * temp1[j]) / J), C);
			pet1[j] = k[j] * pet;
			System.out.println("monthwise pet: "+pet1[j]);
			sumpet = sumpet + pet1[j];
		}
	System.out.println("Total sumpet (sup) is" +sumpet);
	return sumpet;
	}
}

