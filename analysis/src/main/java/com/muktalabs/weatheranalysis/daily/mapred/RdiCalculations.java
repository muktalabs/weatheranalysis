package com.muktalabs.weatheranalysis.daily.mapred;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.muktalabs.weatheranalysis.daily.DailyWeather;
import com.muktalabs.weatheranalysis.monthly.MonthlyWeather;
import com.muktalabs.weatheranalysis.monthly.MonthlyWeatherHbaseOperations;


public class RdiCalculations {

	public static float calculatealphanot(int stationcode,int year)
	{   int i = 0;
		// i need a array list here for all the months from monthly temperature //
		List <MonthlyWeather> rd= new ArrayList<MonthlyWeather>();
		float YearlyPpt=0;
		try {
			rd = MonthlyWeatherHbaseOperations.get(stationcode, year);
		} catch (IOException e) {
			e.printStackTrace();
		}
   for(MonthlyWeather rm:rd)
		{ 
	   float g=rm.getPrecipitation();
	   System.out.println(g);
		YearlyPpt=YearlyPpt+rm.getPrecipitation();
	  	i++;
	  	}
		float pet = 1;
		//Retrieve PET from method (for that year . for that station)
		float alphanot = YearlyPpt/pet ;
		//Calcuate standard rdi
		
	return alphanot;	
	}
}

	
	

