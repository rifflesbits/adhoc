package com.adhoc.homework.slcsp.model;

import org.beanio.annotation.Field;
import org.beanio.annotation.Record;

/**
 * Represents a record from the zips input file
 */
@Record
public class ZipsRecord {
	
	@Field(ordinal=1)
	private String zipCode;
	
	@Field(ordinal=2)
	private String state;
	
	@Field(ordinal=3)
	private String countyCode;
	
	@Field(ordinal=4)
	private String countyName;
	
	@Field(ordinal=5)
	private String rateArea;

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getRateArea() {
		return rateArea;
	}

	public void setRateArea(String rateArea) {
		this.rateArea = rateArea;
	}

	@Override
	public String toString() {
		return "ZipsRecord [zipCode=" + zipCode + ", state=" + state + ", countyCode=" + countyCode + ", countyName="
				+ countyName + ", rateArea=" + rateArea + "]";
	}

}
