package com.muktalabs.weatheranalysis.controller;

public class StationPixel {
    
	String stationCode;
	String x;
	String y;
	
	public StationPixel(String stationCode, String x, String y) {
		super();
		this.stationCode = stationCode;
		this.x = x;
		this.y = y;
	}
	
	
	public String getStationCode() {
		return stationCode;
	}
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "StationPixel [stationCode=" + stationCode + ", x=" + x + ", y="
				+ y + "]";
	}
}
