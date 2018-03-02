package com.adhoc.homework.slcsp;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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
	 * 
	 * @param pOutputWriter
	 * 		The writer for writing the output file 
	 */
	public void processReport(Writer pOutputWriter) {

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
		
		adhocCsvFileWriter.writeSlcspFile(zipMapToSlcspRate, pOutputWriter);
		
		logData(zipCodeListFromSlcpRecList, zipMapToStateRateAreaSet,
				rateAreaMapToSilverPlanRateSet);
		
		logger.info("Completed processing report - wrote output file !" );
	}


	
	/**
	 * Displays interesting data used in processing the solution
	 *  
	 * @param zipCodeListFromSlcpRecList
	 * 
	 * @param zipMapToStateRateAreaSet
	 * 
	 * @param rateAreaMapToSilverPlanRateSet
	 */
	void logData(List<String> zipCodeListFromSlcpRecList, 
			Map<String, Set<StateRateArea>> zipMapToStateRateAreaSet,
			Map<StateRateArea, SortedSet<BigDecimal>> rateAreaMapToSilverPlanRateSet){
		
		System.out.println("\n\nLogged Data:\n\n");
		
		for(String iZip : zipCodeListFromSlcpRecList){
			
			System.out.print(iZip + ": ");
			
			Set<StateRateArea> stateAreaRateSet = zipMapToStateRateAreaSet.get(iZip);
			
			System.out.print(stateAreaRateSet);
			
			if(stateAreaRateSet != null && stateAreaRateSet.size() == 1){
				
				StateRateArea stateRateArea = stateAreaRateSet.iterator().next();
				
				SortedSet<BigDecimal>  rates = rateAreaMapToSilverPlanRateSet.get(stateRateArea);
				
				System.out.print(rates);
			}
			
			System.out.print("\n");
		}
	}
	
	/**
	 * Gets a writer to use for the output file 
	 * 
	 * @return
	 * 		a writer to use for the output file
	 */
	private Writer getFileWriter(){
		
		Writer fileWriter = null;
		
		try {
			fileWriter = new FileWriter("slcsp.csv");
			
		} catch (IOException e) {
			
			logger.severe("Couldn't create output file writer");
		}
		
		return fileWriter;
	}	
	
	/**
	 * Main entry point method
	 * 
	 * @param args
	 * 		Not used
	 */
	public static void main(String[] args) {

		SlcspLauncher slcspLauncher = new SlcspLauncher();

		Writer fileWriter = slcspLauncher.getFileWriter();
		
		slcspLauncher.processReport(fileWriter);
	}

}

