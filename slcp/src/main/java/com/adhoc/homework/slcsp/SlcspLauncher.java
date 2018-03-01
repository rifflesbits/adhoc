package com.adhoc.homework.slcsp;

import java.math.BigDecimal;
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
	
	private AdhocCsvFileWriter adhocCsvFileWriter;

	
	public SlcspLauncher() {

		slcspProcessor = new SlcspProcessor();
		
		adhocCsvFileWriter = new AdhocCsvFileWriter();
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

		// create an ordered map of zip codes pointing to their slcsp rates
		// this collection is the final solution - to be written to an output file
		LinkedHashMap<String, BigDecimal> zipMapToSlcspRate = new LinkedHashMap<String, BigDecimal>();
		
		// populate the map containing the rate solution
		for (String iZipCode : zipCodeListFromSlcpRecList) {

			BigDecimal slcspRate = slcspProcessor.getSlcspRateForZip(iZipCode, zipMapToStateRateAreaSet,
					rateAreaMapToSilverPlanRateSet);
			
			zipMapToSlcspRate.put(iZipCode, slcspRate);
		}
		
		adhocCsvFileWriter.writeSlcspFile(zipMapToSlcspRate);
		
		logger.info("Completed processing report - wrote file!");

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


