package com.adhoc.homework.slcsp;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

import org.beanio.BeanWriter;
import org.beanio.StreamFactory;
import org.beanio.builder.CsvParserBuilder;
import org.beanio.builder.StreamBuilder;

import com.adhoc.homework.slcsp.model.SlcspHeaderRecord;
import com.adhoc.homework.slcsp.model.SlcspRecord;

/**
 * Utility for writing csv file
 */
public class AdhocCsvFileWriter {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	
	/**
	 * Writes the slcsp output file using the provided input records
	 * 
	 * @param zipMapToSlcspRate
	 * 		An ordered map of zip code keys and their associated rates
	 */
	public void writeSlcspFile(LinkedHashMap<String, BigDecimal> zipMapToSlcspRate){

		Writer slcspFileWriter = null;
		
		try {			
			slcspFileWriter = new FileWriter("Slcsp.csv");
			
		} catch (IOException e) {
			
			logger.severe("Couldn't create file Slcsp.csv" + e);
			
			return;
		}			
		
		StreamFactory streamFactory = StreamFactory.newInstance();
		
		StreamBuilder streamBuilder = new StreamBuilder("SlcspFile")
				.format("csv")
				.parser(new CsvParserBuilder())
				.addRecord(SlcspHeaderRecord.class).maxOccurs(1)
				.addRecord(SlcspRecord.class);
		
		streamFactory.define(streamBuilder);
				
		BeanWriter beanWriter = streamFactory.createWriter("SlcspFile", slcspFileWriter);
				
		// write one line for the column header rec
		beanWriter.write(new SlcspHeaderRecord());
		
		// write all the data recs
		for(String iZip : zipMapToSlcspRate.keySet()){
			
			BigDecimal rate = zipMapToSlcspRate.get(iZip);
			
			SlcspRecord slcspRecord = new SlcspRecord();
			
			slcspRecord.setZipCode(iZip);
			slcspRecord.setRate(rate);
			
			beanWriter.write(slcspRecord);
		}		
		
		beanWriter.flush();
		beanWriter.close();
	}	
	
}
