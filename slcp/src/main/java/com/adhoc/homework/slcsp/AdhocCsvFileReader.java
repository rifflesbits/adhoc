package com.adhoc.homework.slcsp;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import org.beanio.builder.CsvParserBuilder;
import org.beanio.builder.StreamBuilder;

import com.adhoc.homework.slcsp.model.PlansHeaderRecord;
import com.adhoc.homework.slcsp.model.PlansRecord;
import com.adhoc.homework.slcsp.model.SlcspHeaderRecord;
import com.adhoc.homework.slcsp.model.SlcspRecord;
import com.adhoc.homework.slcsp.model.ZipsHeaderRecord;
import com.adhoc.homework.slcsp.model.ZipsRecord;

public class AdhocCsvFileReader {
	
	public List<SlcspRecord> readSlcspFile(){

		List<SlcspRecord> rSlcspRecList = new ArrayList<SlcspRecord>();

		InputStream zipsFileInStream = getClass().getResourceAsStream("/slcsp.csv");
		
		Reader csvReader = new InputStreamReader(zipsFileInStream);		
		
		StreamFactory streamFactory = StreamFactory.newInstance();
		
		StreamBuilder streamBuilder = new StreamBuilder("SlcspFile")
				.format("csv")
				.parser(new CsvParserBuilder())
				.addRecord(SlcspHeaderRecord.class).maxOccurs(1)
				.addRecord(SlcspRecord.class);
		
		streamFactory.define(streamBuilder);
		
		BeanReader beanReader = streamFactory.createReader("SlcspFile", csvReader);
		
		Object oRecord = null;
		
		while( (oRecord = beanReader.read()) != null){
			
			if(oRecord instanceof SlcspRecord){

				SlcspRecord slcspRecord = (SlcspRecord)oRecord;
				
				rSlcspRecList.add(slcspRecord);				
			}
		}
		
		return rSlcspRecList;		
	}
	
	
	public List<ZipsRecord> readZipsFile(List<String> pZipCodesToReadList) {
		
		if(pZipCodesToReadList == null || pZipCodesToReadList.isEmpty()){
		
			return null;
		}
		
		List<ZipsRecord> rZipsRecList = new ArrayList<ZipsRecord>();

		InputStream zipsFileInStream = getClass().getResourceAsStream("/zips.csv");
		
		Reader csvReader = new InputStreamReader(zipsFileInStream);		
		
		StreamFactory streamFactory = StreamFactory.newInstance();
		
		StreamBuilder streamBuilder = new StreamBuilder("zipsFile")
				.format("csv")
				.parser(new CsvParserBuilder())
				.addRecord(ZipsHeaderRecord.class).maxOccurs(1)
				.addRecord(ZipsRecord.class);
		
		streamFactory.define(streamBuilder);
		
		BeanReader beanReader = streamFactory.createReader("zipsFile", csvReader);
		
		Object oRecord = null;
		
		while( (oRecord = beanReader.read()) != null){
			
			if(oRecord instanceof ZipsRecord){

				ZipsRecord iZipsRecord = (ZipsRecord)oRecord;
				
				String zipCode = iZipsRecord.getZipCode();
				
				if(pZipCodesToReadList.contains(zipCode)){
				
					rZipsRecList.add(iZipsRecord);	
				}			
			}
		}
		
		return rZipsRecList;
	}
	
	
	public List<PlansRecord> readSilverPlansFromFile(){
		
		List<PlansRecord> rPlansRecList = new ArrayList<PlansRecord>();
		
		InputStream plansFileInStream = getClass().getResourceAsStream("/plans.csv");
		
		Reader csvReader = new InputStreamReader(plansFileInStream);		
		
		StreamFactory streamFactory = StreamFactory.newInstance();
		
		StreamBuilder streamBuilder = new StreamBuilder("plansFile")
				.format("csv")
				.parser(new CsvParserBuilder())
				.addRecord(PlansHeaderRecord.class).maxOccurs(1)
				.addRecord(PlansRecord.class);
		
		streamFactory.define(streamBuilder);
		
		BeanReader beanReader = streamFactory.createReader("plansFile", csvReader);
		
		Object oRecord = null;
		
		while( (oRecord = beanReader.read()) != null){
			
			if(oRecord instanceof PlansRecord){

				PlansRecord iPlansRecord = (PlansRecord)oRecord;

				String metalLevel = iPlansRecord.getMetalLevel();
				
				if("SILVER".equalsIgnoreCase(metalLevel)){
				
					rPlansRecList.add(iPlansRecord);
				}							
			}
		}
		
		return rPlansRecList;
	}

}
