package com.adhoc.homework.slcsp.model;

import java.math.BigDecimal;

import org.beanio.annotation.Field;
import org.beanio.annotation.Record;

/**
 * Represents a record from the Plans input csv file
 */
@Record
public class PlansRecord {
	
	@Field(ordinal=1)
	private String planId;
	
	@Field(ordinal=2)
	private String state;
	
	@Field(ordinal=3)
	private String metalLevel;
	
	@Field(ordinal=4)
	private BigDecimal rate;
	
	@Field(ordinal=5)
	private String rateArea;

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMetalLevel() {
		return metalLevel;
	}

	public void setMetalLevel(String metalLevel) {
		this.metalLevel = metalLevel;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getRateArea() {
		return rateArea;
	}

	public void setRateArea(String rateArea) {
		this.rateArea = rateArea;
	}

	@Override
	public String toString() {
		return "PlansRecord [planId=" + planId + ", state=" + state + ", metalLevel=" + metalLevel + ", rate=" + rate
				+ ", rateArea=" + rateArea + "]";
	}
	
}
