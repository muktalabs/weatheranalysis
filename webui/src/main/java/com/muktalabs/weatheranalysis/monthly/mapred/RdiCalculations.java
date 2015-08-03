package com.muktalabs.weatheranalysis.monthly.mapred;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.muktalabs.weatheranalysis.monthly.MonthlyWeather;
import com.muktalabs.weatheranalysis.monthly.MonthlyWeatherHbaseOperations;
import com.muktalabs.weatheranalysis.monthly.mapred.alphanot;

public class RdiCalculations {
	static int n1=0;
	static double d[]=new double[15];
	
	//static int multiplier;
	public static double calculatealphanot(int stationcode, int year) throws Exception{
		
		 double pet;
		String stationcode1="" +stationcode;
		// int i = 0;
		// i need a array list here for all the months from monthly temperature
		// //
		List<MonthlyWeather> rd = new ArrayList<MonthlyWeather>();
		float YearlyPpt = 0;
		try {
			rd = MonthlyWeatherHbaseOperations.get(stationcode, year);
			if(rd.isEmpty()){
				
				return 0;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (MonthlyWeather rm : rd) {
			float g = rm.getPrecipitation();
			//System.out.println(g);
			
		YearlyPpt = YearlyPpt + g;

		}
		alphanot alpha=new alphanot();
		  			pet=alpha.getkvalues(stationcode1,year);
		  			//if(pet==10000)
		  				//System.out.println("Data unavailable");
               
		System.out.println("Pet as calculated" + pet );
		// Retrieve PET from method (for that year . for that station)
		System.out.println("Total Yearly PPT = "+YearlyPpt+", for year = "+year);
	//if(pet<10000){
		if(pet!=0 && pet!=10000){
		double alphanot = YearlyPpt / pet;
		// Calcuate standard rdi
		if(alphanot>=0){
			return alphanot;
		}
		else{
			return 0;
		}
		}else{
			
			return 0;
		}
	//}
	//else
	//return 10000;
	}
	public static double alphabar(int Stationcode) throws Exception {
		int year=2005;
		Calendar rightNow = Calendar.getInstance();
		int c = rightNow.get(Calendar.YEAR);
		double totalalphanot = 0;
		int divisor = 0;
		int i=0;
		while ( year <= 2014) { // Replace 2014 with c for
												// current year and year by 2000
			d[i]=calculatealphanot(Stationcode, year);					// for whole n years data
			totalalphanot = totalalphanot + calculatealphanot(Stationcode, year);
i++;
			//System.out.println("I am in  while");
			if ((calculatealphanot(Stationcode, year))==0) {
				year++;
				continue;
			} else
				divisor++;
			n1=divisor;
			year++;
		}
		return totalalphanot / divisor;
	}

	public static double normalizedrdi(int stationcode, int year) throws Exception {
		double num_rdi = calculatealphanot(stationcode, year);
		if(num_rdi==0){
			return 10000;
		}
		double den_rdi = alphabar(stationcode);
		double nrdi = (num_rdi/den_rdi) - 1 ;
		final double h =Math.log(alphabar(stationcode));
        double summation=0;
        int counterr=0;
		double num_srd = ((Math.log(num_rdi)-Math.log(den_rdi)))   ;
		for (int year1=2005;year1<=2014;year1++){
			double f=calculatealphanot(stationcode, year1);
		if(f!=0){
			double varia=Math.pow((h-Math.log(f)),2);
		summation=summation+varia;
		counterr++;
		System.out.println("counterr is"+counterr);
		}
		}
		for(int i=0;i<d.length;i++)
		{
			System.out.println("calcu calculations"+d[i]);
		}
		double den_srd = Math.sqrt(summation/counterr);
        System.out.println("divisor is" + n1);
		System.out.println("denrdi is"+den_rdi);
		System.out.println("Alphanot is"+num_rdi);
		System.out.println( "Numerator is "+num_srd);
        System.out.println("Denominator is"+summation);
        double srdi = num_srd/den_srd;
		
        System.out.println("Normalized rdi is :" + nrdi);
          System.out.println("Standard rdi id :" + srdi);
		return srdi;
	}	
/*public static void main(String args[]) throws Exception
{
double x =normalizedrdi(420270, 2012);

System.out.println("Final rdi is " + x );

}*/
}
