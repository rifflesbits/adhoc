package com.adhoc.homework.slcsp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;

import com.adhoc.homework.slcsp.model.PlansRecord;
import com.adhoc.homework.slcsp.model.SlcspRecord;
import com.adhoc.homework.slcsp.model.StateRateArea;
import com.adhoc.homework.slcsp.model.ZipsRecord;

public class SlcspProcessor {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	private AdhocCsvFileReader adhocCsvFileReader;
		
	
	public SlcspProcessor() {
		
		adhocCsvFileReader = new AdhocCsvFileReader();
	}
	
	
	Map<String, Set<StateRateArea>> getZipMapToStateRateAreaSet(
			Map<String, List<ZipsRecord>> pZipCodeMapToZipRecList) {
					
		Map<String, Set<StateRateArea>> rZipMapToStateRateAreaSet = new HashMap<String, Set<StateRateArea>>();
		
		// loop through all the zip codes				
		for(String iZip : pZipCodeMapToZipRecList.keySet()){
			
			List<ZipsRecord> zipRecList = pZipCodeMapToZipRecList.get(iZip);
			
			Set<StateRateArea> stateRateAreaSet = new HashSet<StateRateArea>();
			
			// loop through the zip records for each zip code
			// storing the stateRateAreas in a set for each zip code
			for(ZipsRecord iZipsRecord : zipRecList){
				
				StateRateArea stateRateArea = new StateRateArea(iZipsRecord);
				
				stateRateAreaSet.add(stateRateArea);
			}
			
			rZipMapToStateRateAreaSet.put(iZip, stateRateAreaSet);			
		}
		
		return rZipMapToStateRateAreaSet;		
	}
	
	
	
	
	Map<String, List<ZipsRecord>> getZipMapToZipsRecList(){
		
		List<String> zipCodeListFromSlcpRecList = getRequestedZipCodeList();
		
		Map<String, List<ZipsRecord>> zipCodeMapToZipsRecList = new HashMap<String, List<ZipsRecord>>();
		
		List<ZipsRecord> zipRecList = adhocCsvFileReader.readZipsFile(zipCodeListFromSlcpRecList);
		
		for(ZipsRecord iZipsRecord : zipRecList){
			
			String zipCode = iZipsRecord.getZipCode();
			
			List<ZipsRecord> zipsRecordList = zipCodeMapToZipsRecList.get(zipCode);
			
			if(zipsRecordList == null ){
				
				zipsRecordList = new ArrayList<ZipsRecord>();
				
				zipCodeMapToZipsRecList.put(zipCode, zipsRecordList);				
			}
						
			zipsRecordList.add(iZipsRecord);			
		}
		
		return zipCodeMapToZipsRecList;
	}
	
	
	public List<String> getRequestedZipCodeList(){
		
		List<SlcspRecord> slcpRecList = adhocCsvFileReader.readSlcspFile();
		
		List<String> rZipCodeList = new ArrayList<String>();
		
		for(SlcspRecord iSlcspRecord : slcpRecList){
			
			String zipCode = iSlcspRecord.getZipCode();
			
			rZipCodeList.add(zipCode);			
		}
		return rZipCodeList;
	}
	
	
	
	public Map<StateRateArea, SortedSet<BigDecimal>> getRateAreaMapToSilverPlanRateSet() {

		Map<StateRateArea, SortedSet<BigDecimal>> rateAreaMapToSilverPlanRateSet = new HashMap<StateRateArea, SortedSet<BigDecimal>>();
		
		List<PlansRecord> silverPlanRecList = adhocCsvFileReader.readSilverPlansFromFile();
		
		for(PlansRecord iPlanRec : silverPlanRecList){
			
			StateRateArea stateRateArea = new StateRateArea(iPlanRec);			
			
			// will be non-null if having already processed this stateRateArea
			// at least once
			SortedSet<BigDecimal> rateSet = rateAreaMapToSilverPlanRateSet.get(stateRateArea);
			
			if(rateSet == null){
				
				// the first time we're encountering this stateRateArea, so 
				// create a new set and add it to the map
				rateSet = new TreeSet<BigDecimal>();
				
				rateAreaMapToSilverPlanRateSet.put(stateRateArea, rateSet);
			}
			
			BigDecimal rate = iPlanRec.getRate();
			
			rateSet.add(rate);		
		}		
		
		return rateAreaMapToSilverPlanRateSet;
	}
	
	
}

