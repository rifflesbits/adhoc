package com.adhoc.homework.slcsp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.logging.Logger;

import com.adhoc.homework.slcsp.model.StateRateArea;
import com.adhoc.homework.slcsp.model.ZipsRecord;

/**
 * Contains main entry point for running the SLCSP script
 */
public class SlcspLauncher {

	private Logger logger = Logger.getLogger(getClass().getName());

	private SlcspProcessor slcspProcessor;

	public SlcspLauncher() {

		slcspProcessor = new SlcspProcessor();
	}

	
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
			
			logger.warning("sortedRateSet size not >= 2, Can't get slcsp rate");
		
			rSlcspRate = null;
		}		
		
		return rSlcspRate;
	}
	
	/**
	 * Processes the input files, creates the needed data structures
	 * and writes the output file
	 */
	public void processReport() {

		// get a List of zip codes in the same order as the slcsp file
		List<String> zipCodeListFromSlcpRecList = slcspProcessor.getRequestedZipCodeList();
		
		// get a map of requested zip code keys pointing to each of their zips csv file recs
		Map<String, List<ZipsRecord>> zipMapToZipRecList = slcspProcessor
				.getZipMapToZipsRecList(zipCodeListFromSlcpRecList);

		// get a map of zip code keys pointing to each of their StateRateArea's
		Map<String, Set<StateRateArea>> zipMapToStateRateAreaSet = slcspProcessor
				.getZipMapToStateRateAreaSet(zipMapToZipRecList);

		// get a map of StateRateArea keys pointing to their SortedSet of rates
		Map<StateRateArea, SortedSet<BigDecimal>> rateAreaMapToSilverPlanRateSet = slcspProcessor
				.getRateAreaMapToSilverPlanRateSet();

		Map<String, BigDecimal> zipMapToSlcspRate = new LinkedHashMap<String, BigDecimal>();
		
		for (String iZipCode : zipCodeListFromSlcpRecList) {

			BigDecimal slcspRate = getSlcspRateForZip(iZipCode, zipMapToStateRateAreaSet, rateAreaMapToSilverPlanRateSet);
			
			zipMapToSlcspRate.put(iZipCode, slcspRate);
		}
		
		System.out.println(zipMapToSlcspRate);

	}

	/**
	 * Main entry point method
	 * 
	 * @param args
	 * 		Not used
	 */
	public static void main(String[] args) {

		SlcspLauncher slcspLauncher = new SlcspLauncher();

		slcspLauncher.processReport();
	}

}
