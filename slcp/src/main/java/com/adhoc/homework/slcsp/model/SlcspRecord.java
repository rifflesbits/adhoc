package com.adhoc.homework.slcsp.model;

import org.beanio.annotation.Field;
import org.beanio.annotation.Record;

@Record
public class SlcspRecord {
	
	@Field(ordinal=1)
	private String zipCode;
	
	@Field(ordinal=2)
	private String rate;

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "SlcspRecord [zipCode=" + zipCode + ", rate=" + rate + "]";
	}

}
