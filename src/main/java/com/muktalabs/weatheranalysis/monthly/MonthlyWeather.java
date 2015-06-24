package com.muktalabs.weatheranalysis.monthly;

import org.apache.hadoop.hbase.util.Bytes;


public class MonthlyWeather {

	private int stationCode = 0;
	private int year = 0;
	private int month = 0;

	private float minTemp = 0; 
	private float maxTemp = 0; 
	private float avgTemp = 0; 
	private float precipitation = 0;
	private float dewpoint = 0;
	
	
	public MonthlyWeather(){
		
	}
	
	public MonthlyWeather(int stationCode, int year, int month) {
		this.stationCode = stationCode;
		this.year = year;
		this.month = month;
	}
	
	public MonthlyWeather(int stationCode, int year, int month,
			float minTemp, float maxTemp, float avgTemp, float precipitation,float dewpoint){
		super();
		this.stationCode = stationCode;
		this.year = year;
		this.month = month;
		this.minTemp = minTemp;
		this.maxTemp = maxTemp;
		this.avgTemp = avgTemp;
		this.precipitation = precipitation;
	    this.dewpoint = dewpoint;
	}
	

	
	public float getDewpoint()
	{
	return dewpoint;	
	}
    public void setDewpoint(float dewpoint)
    {
    this.dewpoint = dewpoint;
    }
	public int getStationCode() {
		return stationCode;
	}

	public void setStationCode(int stationCode) {
		this.stationCode = stationCode;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public float getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(float minTemp) {
		this.minTemp = minTemp;
	}

	public float getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(float maxTemp) {
		this.maxTemp = maxTemp;
	}

	public float getAvgTemp() {
		return avgTemp;
	}

	public void setAvgTemp(float avgTemp) {
		this.avgTemp = avgTemp;
	}

	public float getPrecipitation() {
		return precipitation;
	}
	


	public void setPrecipitation(float precipitation) {
		this.precipitation = precipitation;
	}

	public byte[] pack() {
		
		int yearmonth = year * 100 + month;
		
		byte[] res1 = Bytes.toBytes(stationCode);
		byte[] res2 = Bytes.toBytes(yearmonth);
		byte[] result = new byte[12];
		
		for (int i = 0; i < 4; i++) {
			result[i] = res1[i];
		}

		for (int i = 0; i < 4; i++) {
			result[4 + i] = res2[i];
		}

		return result;
	}

	public static MonthlyWeather unpack(byte[] result) {

		 byte[] res1 = new byte[4];
		byte[] res2 = new byte[4];
		
		for (int i = 0; i < 4; i++) {
			res1[i] = result[i];
		}
		
		for (int i = 0; i < 4; i++) {
			res2[i] = result[4 + i];
		}
		
		int stationcode = Bytes.toInt(res1);
		int year = Bytes.toInt(res2) / 100;
		int month = Bytes.toInt(res2) % 100;
		return new MonthlyWeather(stationcode, year, month);
	}

	@Override
	public String toString() {
		return "MonthlyWeather [stationCode=" + stationCode + ", year="
				+ year + ", month=" + month  + ", minTemp=" + minTemp
				+ ", maxTemp=" + maxTemp + ", avgTemp=" + avgTemp
				+ ", precipitation=" + precipitation
				+ ", dewpoint=" + dewpoint + "]";
	}
}
	

