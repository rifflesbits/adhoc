package com.adhoc.homework.slcsp;

import java.util.List;
import java.util.logging.Logger;

import com.adhoc.homework.slcsp.model.PlansRecord;
import com.adhoc.homework.slcsp.model.ZipsRecord;

public class SlcspProcessor {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	private InputFileReader inputFileReader;
	
	public SlcspProcessor() {
		
		inputFileReader = new InputFileReader();
	}
	
	public List<ZipsRecord> getZipsRecList(){
			
		List<ZipsRecord> zipsRecList = inputFileReader.readZipsFile();
	
		logger.info("zipsRecList size: " + (zipsRecList == null ? 0 : zipsRecList.size()));
		
		return zipsRecList;
	}
	
	public List<PlansRecord> getPlansRecList(){
		
		List<PlansRecord> plansRecList = inputFileReader.readPlansFile();
		
		logger.info("plansRecList size: " + (plansRecList == null ? 0 : plansRecList.size()));
		
		return plansRecList;
	}
}
