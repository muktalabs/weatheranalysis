/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author  Manish Vyas
 * @version 1.0
 * @since   2015-06-19
 */

package com.muktalabs.weatheranalysis.daily;

import org.apache.hadoop.hbase.util.Bytes;


public class DailyWeather {

	private int stationCode = 0;
	private int year = 0;
	private int month = 0;
    private int day = 0;
    
	private float minTemp = 0; 
	private float maxTemp = 0; 
	private float avgTemp = 0; 
	private float precipitation = 0;
	private float dewpoint = 0;
	
	
	public DailyWeather(){
		
	}
	
	public DailyWeather(int stationCode, int year, int month,int day) {
		this.stationCode = stationCode;
		this.year = year;
		this.month = month;
		this.day = day; 
	}
	
	public DailyWeather(int stationCode, int year, int month,int day,
			float minTemp, float maxTemp, float avgTemp, float precipitation,float dewpoint){
		super();
		this.stationCode = stationCode;
		this.day = day;
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
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}


	public void setPrecipitation(float precipitation) {
		this.precipitation = precipitation;
	}

	public byte[] pack() {
		
		int yearmonthdd = year * 10000 + month*100 + day;
		
		byte[] res1 = Bytes.toBytes(stationCode);
		byte[] res2 = Bytes.toBytes(yearmonthdd);
		byte[] result = new byte[12];
		
		for (int i = 0; i < 4; i++) {
			result[i] = res1[i];
		}

		for (int i = 0; i < 4; i++) {
			result[4 + i] = res2[i];
		}

		return result;
	}

	public static DailyWeather unpack(byte[] result) {

		 byte[] res1 = new byte[4];
		byte[] res2 = new byte[4];
		
		for (int i = 0; i < 4; i++) {
			res1[i] = result[i];
		}
		
		for (int i = 0; i < 4; i++) {
			res2[i] = result[4 + i];
		}
		
		int stationcode = Bytes.toInt(res1);
		int year = Bytes.toInt(res2) / 10000;
		int day = Bytes.toInt(res2) % 100;
		int month = Bytes.toInt(res2)/100 % 100;
		return new DailyWeather(stationcode, year, month,day);
	}

	@Override
	public String toString() {
		return "DailyWeather [stationCode=" + stationCode + ", year="
				+ year + ", month=" + month + ",day=" + day + ", minTemp=" + minTemp
				+ ", maxTemp=" + maxTemp + ", avgTemp=" + avgTemp
				+ ", precipitation=" + precipitation
				+ ", dewpoint=" + dewpoint + "]";
	}
}
	

