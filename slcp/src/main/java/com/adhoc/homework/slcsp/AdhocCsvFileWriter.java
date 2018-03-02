package com.adhoc.homework.slcsp;

import java.io.Writer;
import java.math.BigDecimal;
import java.util.LinkedHashMap;

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

	
	/**
	 * Writes the slcsp output to the specified writer, using the provided input
	 * records
	 * 
	 * @param zipMapToSlcspRate
	 * 		The records to write
	 * 
	 * @param pWriter
	 * 		The writer for writing the output
	 */
	public void writeSlcspFile(LinkedHashMap<String, BigDecimal> zipMapToSlcspRate, Writer pWriter){
				
		StreamFactory streamFactory = StreamFactory.newInstance();
		
		StreamBuilder streamBuilder = new StreamBuilder("SlcspFile")
				.format("csv")
				.parser(new CsvParserBuilder())
				.addRecord(SlcspHeaderRecord.class).maxOccurs(1)
				.addRecord(SlcspRecord.class);
		
		streamFactory.define(streamBuilder);
				
		BeanWriter beanWriter = streamFactory.createWriter("SlcspFile", pWriter);
				
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
