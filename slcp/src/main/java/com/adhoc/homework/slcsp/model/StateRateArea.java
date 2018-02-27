package com.adhoc.homework.slcsp.model;

public class StateRateArea {
	
	private String state;
	
	private String rateArea;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRateArea() {
		return rateArea;
	}

	public void setRateArea(String rateArea) {
		this.rateArea = rateArea;
	}

	@Override
	public String toString() {
		return state + rateArea;
	}

	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rateArea == null) ? 0 : rateArea.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StateRateArea other = (StateRateArea) obj;
		if (rateArea == null) {
			if (other.rateArea != null)
				return false;
		} else if (!rateArea.equals(other.rateArea))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
	
}
