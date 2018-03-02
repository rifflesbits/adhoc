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

/**
 * Contains utility methods for processing the data
 */
public class SlcspProcessor {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	private AdhocCsvFileReader adhocCsvFileReader;
		
	
	public SlcspProcessor() {
		
		adhocCsvFileReader = new AdhocCsvFileReader();
	}
	
	
	/**
	 * Gets a map of zip codes associated with Sets of {@link StateRateArea}
	 * 
	 * @param pZipCodeMapToZipRecList
	 * 		The map of zipcodes and their zip file rec list
	 * 
	 * @return
	 * 		a map of zip codes associated with Sets of {@link StateRateArea}
	 */
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
	
	
	
	/**
	 * Gets a Map of zip codes pointing to their {@link ZipsRecord} 
	 * objects red in from the input file
	 *  
	 * @param pZipCodeListFromSlcpRecList
	 * 	  A list of zip codes to use in creating the lookup structure
	 *  
	 * @return
	 * 		A Map of zip codes and their associated ZipsRecord's
	 */
	Map<String, List<ZipsRecord>> getZipMapToZipsRecList(List<String> pZipCodeListFromSlcpRecList){
		
		// the return collection
		Map<String, List<ZipsRecord>> zipCodeMapToZipsRecList = new HashMap<String, List<ZipsRecord>>();
		
		// get all the zips file records corresponding to the requested zip codes
		List<ZipsRecord> zipRecList = adhocCsvFileReader.readZipsFile(pZipCodeListFromSlcpRecList);
		
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
	
	
	/**
	 * Gets a list of zip codes from the slcsp input file
	 * 
	 * These are the requested zip codes for which we must specify 
	 * the rate
	 * 
	 * @return
	 * 		The list of zip codes from the input request file
	 */
	public List<String> getRequestedZipCodeList(){
		
		List<SlcspRecord> slcpRecList = adhocCsvFileReader.readSlcspFile();
		
		List<String> rZipCodeList = new ArrayList<String>();
		
		for(SlcspRecord iSlcspRecord : slcpRecList){
			
			String zipCode = iSlcspRecord.getZipCode();
			
			rZipCodeList.add(zipCode);			
		}
		return rZipCodeList;
	}
	
	
	/**
	 * Gets a Map of {@link StateRateArea} keys pointing to their associated 
	 * sorted set of rates
	 * 
	 * @return
	 * 		A Map of {@link StateRateArea} keys and their Sorted set of rates
	 */
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
	
	/**
	 * Gets the slcsp rate for the specified zip code, using the lookup collections
	 * 
	 * @param iZipCode
	 * 		The zip code for which the slcsp rate should be returned
	 * 
	 * @param pZipMapToStateRateAreaSet
	 * 		The collection for looking up the zip's state rate areas
	 * 
	 * @param pRateAreaMapToSilverPlanRateSet
	 * 		The collection for looking up the rates for the rate area
	 * 
	 * @return
	 * 		the slcsp rate for the specified zip code
	 */
	BigDecimal getSlcspRateForZip(String iZipCode, 
			Map<String,	Set<StateRateArea>> pZipMapToStateRateAreaSet,
			Map<StateRateArea, SortedSet<BigDecimal>> pRateAreaMapToSilverPlanRateSet){
		
		BigDecimal rSlcspRate = null;
		
		Set<StateRateArea> stateRateAreaSetForZip = pZipMapToStateRateAreaSet.get(iZipCode);

		if(stateRateAreaSetForZip == null){
			
			logger.warning("stateRateAreaSetForZip == null for zip: " + iZipCode + " (Can't get rate)");
			
			return null;
			
		}else if(stateRateAreaSetForZip.size() != 1){
			
			logger.warning("stateRateAreaSetForZip.size() != 1 for zip: " + iZipCode 
					+ " (ambiguous, so can't get rate)");
			
			return null;
		}
		
		StateRateArea stateRateArea = stateRateAreaSetForZip.iterator().next();

		SortedSet<BigDecimal> sortedRateSet = pRateAreaMapToSilverPlanRateSet.get(stateRateArea);

		
		if(sortedRateSet != null && sortedRateSet.size() >= 2){
			
			List<BigDecimal> sortedRateList = new ArrayList<BigDecimal>(sortedRateSet);
			
			rSlcspRate = sortedRateList.get(1);
			
		}else{
			
			logger.warning("sortedRateSet size not >= 2, Can't get slcsp rate for stateRateArea: " + stateRateArea);
		
			rSlcspRate = null;
		}		
		
		return rSlcspRate;
	}	
	
}

