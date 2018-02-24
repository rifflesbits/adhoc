package com.adhoc.homework.slcsp;

import java.util.List;
import java.util.logging.Logger;

import com.adhoc.homework.slcsp.model.PlansRecord;
import com.adhoc.homework.slcsp.model.ZipsRecord;

public class SlcspLauncher {
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	private SlcspProcessor slcspProcessor;
	
	public SlcspLauncher() {
		
		slcspProcessor = new SlcspProcessor();
	}
	
	public void processReport(){
		
		List<ZipsRecord> zipsRecList = slcspProcessor.getZipsRecList();
		
		List<PlansRecord> plansRecList = slcspProcessor.getPlansRecList();
	}
	
	public static void main(String[] args) {
		
		SlcspLauncher slcspLauncher = new SlcspLauncher();
		
		slcspLauncher.processReport();
	}

}
