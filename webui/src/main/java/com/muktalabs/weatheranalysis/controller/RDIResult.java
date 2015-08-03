package com.muktalabs.weatheranalysis.controller;

public class RDIResult {

	private String status;
	private double rdi;
	
	public RDIResult(String status, double rdi) {
		this.status = status;
		this.rdi = rdi;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getRdi() {
		return rdi;
	}

	public void setRdi(double rdi) {
		this.rdi = rdi;
	}

	@Override
	public String toString() {
		return "RDIResult [status=" + status + ", rdi=" + rdi + "]";
	}
}
