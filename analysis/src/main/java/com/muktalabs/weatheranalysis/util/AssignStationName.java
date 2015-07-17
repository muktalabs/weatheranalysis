package com.muktalabs.weatheranalysis.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AssignStationName {
	
	public static void main(String[] args) throws IOException {
	
		File in1 = new File("/mnt/data/workspace/weatheranalysis/analysis/src/main/resources/station_lati_copy.csv");
		File in2 = new File("/mnt/data/workspace/weatheranalysis/analysis/src/main/resources/station_freq_name_pix.txt");
		
		File out = new File("/mnt/data/workspace/weatheranalysis/analysis/src/main/resources/station_freq_name_pix.csv");
		
		new AssignStationName().merge(in1, in2, out);
	}

	private void merge(File in1, File in2, File out) throws IOException {
		
		Scanner sc1 = new Scanner(in1);
		Map<String, String> codeNameMap = new HashMap<String, String>();
		while(sc1.hasNext()){
			String line = sc1.nextLine();
			if(line!=null && line.trim()!=""){
				String[] p = line.split(",");
				codeNameMap.put(p[0], p[2]);
			}
		}
		sc1.close();
		
		FileWriter fw = new FileWriter(out);
		Scanner sc2 = new Scanner(in2);
		fw.append(sc2.nextLine()).append('\n');
		while(sc2.hasNext()){
			String line = sc2.nextLine();
			if(line!=null && line.trim()!=""){
				String[] p = line.split(",");
				
				if(p.length < 3){
					String[] p1 = new String[5];
					Arrays.fill(p1, "");
					System.arraycopy(p, 0, p1, 0, p.length);
					p = p1;
				}
				p[2] = codeNameMap.get(p[1]);
				
				String newLine = p[0]+","+p[1]+","+p[2]+","+p[3]+","+p[4]+",";
				System.out.println(newLine);
				fw.append(newLine);
				fw.append('\n');
			}
		}
		sc2.close();
		fw.close();
	}
}
