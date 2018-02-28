package com.adhoc.homework.slcsp.model;

import org.beanio.annotation.Field;
import org.beanio.annotation.Fields;
import org.beanio.annotation.Record;

/**
 * Represents a column header record from the zips file 
 */
@Record(maxOccurs=1)
@Fields({
	@Field(name="zipCode", literal="zipcode", ordinal=1),
	@Field(name="state", literal="state", ordinal=2),
	@Field(name="countyCode", literal="county_code", ordinal=3),
	@Field(name="countyName", literal="name", ordinal=4),
	@Field(name="rateArea", literal="rate_area", ordinal=5),
})
public class ZipsHeaderRecord {

}
