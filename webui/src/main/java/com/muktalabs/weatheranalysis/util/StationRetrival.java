package com.muktalabs.weatheranalysis.util;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StationRetrival {
	public static boolean loaded = false;
	public static List<String> list = new ArrayList<String>();

	public static synchronized List<String> getfile() throws FileNotFoundException {
		
		if(loaded){
			return list;
		}
		
		InputStream fis = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("station_freq_name_pix.txt");
		Scanner sc = new Scanner(fis);
		while (sc.hasNext()) {
			String line = sc.nextLine();
			if (line != null && line.trim() != "") {
				String[] p = line.split(",");

				int m = Integer.parseInt(p[0]);
				if (m >= 10){
					list.add(line);
				}
			}
		}
		sc.close();
		
		System.out.println("Loaded station data for: ["+list.size()+"] stations");
		loaded = true;
		return list;
	}

	public static String st_name_retrival(String code)
			throws FileNotFoundException {
		Map<String, String> stationame = new HashMap<String, String>();
		String stname = null;
		List<String> lis = new ArrayList<String>();
		lis = getfile();
		
		for (String f : lis) {
			String[] p = f.split(",");

			int m = Integer.parseInt(p[0]);
			if (m >= 10)
				stationame.put(p[1], p[2]);

		}

		for (String cd : stationame.keySet()) {

			if (cd.equals(code)) {
				stname = stationame.get(cd);
				break;
			}
		}
		return stname;
	}

	public static Map<String, String> st_retrival_map()
			throws FileNotFoundException {

		Map<String, String> maphmap = new HashMap<String, String>();

		List<String> lis = new ArrayList<String>();
		lis = getfile();
		
		for (String f : lis) {
			String[] p = f.split(",");

			int m = Integer.parseInt(p[0]);
			if (m >= 10)
				maphmap.put(p[1], p[2]);

		}
		return maphmap;
	}
}
