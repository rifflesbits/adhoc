package com.adhoc.homework.slcsp;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.adhoc.homework.slcsp.model.StateRateArea;
import com.adhoc.homework.slcsp.model.ZipsRecord;

public class SlcspLauncher {
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	private SlcspProcessor slcspProcessor;
	
	public SlcspLauncher() {
		
		slcspProcessor = new SlcspProcessor();
	}
	
	public void processReport(){
						
						
		Map<String, List<ZipsRecord>> requestedZipCodeMapToZipRecList = slcspProcessor.getZipCodeMapToZipsRecList();
				
		for(String iZipCode : requestedZipCodeMapToZipRecList.keySet()){
												
			Set<StateRateArea> stateRateAreaSet = slcspProcessor.getStateRateAreaSetForZipCode(iZipCode,
					requestedZipCodeMapToZipRecList);
			
			System.out.println("zip code: " + iZipCode + ", stateRateAreaSet: " + stateRateAreaSet);
			
		}
	}
	
	public static void main(String[] args) {
		
		SlcspLauncher slcspLauncher = new SlcspLauncher();
		
		slcspLauncher.processReport();
	}

}
