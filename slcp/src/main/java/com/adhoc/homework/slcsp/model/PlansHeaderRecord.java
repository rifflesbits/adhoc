package com.adhoc.homework.slcsp.model;

import org.beanio.annotation.Field;
import org.beanio.annotation.Fields;
import org.beanio.annotation.Record;

@Record(maxOccurs=1)
@Fields({
	@Field(name="planId", literal="plan_id", ordinal=1),
	@Field(name="state", literal="state", ordinal=2),
	@Field(name="metalLevel", literal="metal_level", ordinal=3),
	@Field(name="rate", literal="rate", ordinal=4),
	@Field(name="rateArea", literal="rate_area", ordinal=5),
})
public class PlansHeaderRecord {

}
