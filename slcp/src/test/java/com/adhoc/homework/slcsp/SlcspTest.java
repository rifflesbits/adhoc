package com.adhoc.homework.slcsp;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.io.Resources;

public class SlcspTest {

	@Test
	public void slcspTest() throws IOException{
		
		SlcspLauncher slcspLauncher = new SlcspLauncher();
		
		Writer slcspWriter = new StringWriter();
		
		slcspLauncher.processReport(slcspWriter);
		
		String sSlcspReportActual = slcspWriter.toString();
		
	 	URL slcspUrl = Resources.getResource("slcspSolution.csv");
	 	
	 	String sSlcspExpected = Resources.toString(slcspUrl, Charset.defaultCharset());
	 	
	 	Assert.assertEquals(sSlcspExpected, sSlcspReportActual);		
	}
	
}
