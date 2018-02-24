package com.adhoc.homework.slcsp;

import java.util.List;
import java.util.logging.Logger;

import com.adhoc.homework.slcsp.model.PlansRecord;
import com.adhoc.homework.slcsp.model.SlcspRecord;
import com.adhoc.homework.slcsp.model.ZipsRecord;

public class SlcspProcessor {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	private AdhocCsvFileReader adhocCsvFileReader;
	
	public SlcspProcessor() {
		
		adhocCsvFileReader = new AdhocCsvFileReader();
	}
	
	public List<SlcspRecord> getSlcspRecList(){
		
		List<SlcspRecord>  slcspRecList = adhocCsvFileReader.readSlcspFile();
		
		logger.info("slcspRecList size: " + (slcspRecList == null ? 0 : slcspRecList.size()));
		
		return slcspRecList;
	}
	
	public List<ZipsRecord> getZipsRecList(){
			
		List<ZipsRecord> zipsRecList = adhocCsvFileReader.readZipsFile();
	
		logger.info("zipsRecList size: " + (zipsRecList == null ? 0 : zipsRecList.size()));
		
		return zipsRecList;
	}
	
	public List<PlansRecord> getPlansRecList(){
		
		List<PlansRecord> plansRecList = adhocCsvFileReader.readPlansFile();
		
		logger.info("plansRecList size: " + (plansRecList == null ? 0 : plansRecList.size()));
		
		return plansRecList;
	}
}
