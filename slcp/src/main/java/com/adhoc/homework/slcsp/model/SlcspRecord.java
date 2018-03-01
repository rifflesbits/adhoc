package com.adhoc.homework.slcsp.model;

import java.math.BigDecimal;

import org.beanio.annotation.Field;
import org.beanio.annotation.Record;

/**
 * Represents a record from the slcsp file
 */
@Record
public class SlcspRecord {
	
	@Field(ordinal=1)
	private String zipCode;
	
	@Field(ordinal=2)
	private BigDecimal rate;

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "SlcspRecord [zipCode=" + zipCode + ", rate=" + rate + "]";
	}

}
