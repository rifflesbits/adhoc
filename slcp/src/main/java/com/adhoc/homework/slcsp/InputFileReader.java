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

import com.adhoc.homework.slcsp.model.PlansRecord;
import com.adhoc.homework.slcsp.model.ZipsHeaderRecord;
import com.adhoc.homework.slcsp.model.ZipsRecord;

public class InputFileReader {
	
	
	public List<ZipsRecord> readZipsFile() {
		
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
				
				rZipsRecList.add(iZipsRecord);				
			}
		}
		
		return rZipsRecList;
	}
	
	
	public List<PlansRecord> readPlansFile(){
		
		List<PlansRecord> rPlansRecList = new ArrayList<PlansRecord>();
		
		return rPlansRecList;
	}

}
