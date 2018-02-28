package com.adhoc.homework.slcsp.model;

import org.beanio.annotation.Field;
import org.beanio.annotation.Fields;
import org.beanio.annotation.Record;

/**
 * Represents a header column record from the slcsp csv file
 */
@Record(maxOccurs=1)
@Fields({
	@Field(name="zipCode", literal="zipcode", ordinal=1),
	@Field(name="rate", literal="rate", ordinal=2),
})
public class SlcspHeaderRecord {

}
