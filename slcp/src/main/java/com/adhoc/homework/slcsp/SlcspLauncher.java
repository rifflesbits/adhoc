package com.adhoc.homework.slcsp;

import java.math.BigDecimal;
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

	public void processReport() {

		Map<String, List<ZipsRecord>> zipMapToZipRecList = slcspProcessor.getZipMapToZipsRecList();

		Map<String, Set<StateRateArea>> zipMapToStateRateAreaSet = slcspProcessor
				.getZipMapToStateRateAreaSet(zipMapToZipRecList);

		Map<StateRateArea, Set<BigDecimal>> rateAreaMapToSilverPlanRateSet = slcspProcessor
				.getRateAreaMapToSilverPlanRateSet();

		for (String iZipCode : zipMapToZipRecList.keySet()) {

			Set<StateRateArea> stateRateAreaSet = zipMapToStateRateAreaSet.get(iZipCode);

			if (stateRateAreaSet != null && stateRateAreaSet.size() == 1) {

				StateRateArea stateRateArea = stateRateAreaSet.iterator().next();

				Set<BigDecimal> rateSet = rateAreaMapToSilverPlanRateSet.get(stateRateArea);

				System.out.println("zip code: " + iZipCode + ", stateRateArea: " + stateRateArea + ", rateSet: " + rateSet);

			} else {

				logger.warning("zip: " + iZipCode + " has ambiguous stateRateAreaSet: " + stateRateAreaSet);
			}
		}

	}

	public static void main(String[] args) {

		SlcspLauncher slcspLauncher = new SlcspLauncher();

		slcspLauncher.processReport();
	}

}
