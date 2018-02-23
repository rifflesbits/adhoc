package com.adhoc.homework.slcsp.model;

import org.beanio.annotation.Field;
import org.beanio.annotation.Fields;
import org.beanio.annotation.Record;

@Record
@Fields({
	@Field(name="zipCode", literal="zipcode", ordinal=1),
	@Field(name="rate", literal="rate", ordinal=2),
})
public class SlcspHeaderRecord {

}
