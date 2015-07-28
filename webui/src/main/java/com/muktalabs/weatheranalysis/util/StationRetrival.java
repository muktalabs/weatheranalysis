package com.muktalabs.weatheranalysis.util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class StationRetrival{
	

public static Map<String,String> st_retrival_map() throws FileNotFoundException{
	File fm = new File("/mnt/data/workspace/weatheranalysis/analysis/src/main/resources/station_freq_name_pix.csv");
	FileReader rm=new FileReader(fm);
	Scanner sc1 = new Scanner(rm);
	
		Map<String, String> maphmap = new HashMap<String, String>();
		
	while(sc1.hasNext()){
		String line = sc1.nextLine();
		if(line!=null && line.trim()!=""){
			String[] p = line.split(",");
		
			int m=Integer.parseInt(p[0]);
			if(m>=10)
			maphmap.put(p[1], p[2]);
    }
	}
		sc1.close();
		return maphmap;
	}
}


	


