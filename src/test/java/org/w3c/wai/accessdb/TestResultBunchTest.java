package org.w3c.wai.accessdb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;
import org.w3c.wai.accessdb.om.TestResultsBunch;
import org.w3c.wai.accessdb.utils.JAXBUtils;

public class TestResultBunchTest {

	@Test
	public void test() throws IOException {
		String path = TestResultBunchTest.class.getResource("bunch.json").getFile();
		TestResultsBunch o = new TestResultsBunch();
		TestResultsBunch b = (TestResultsBunch) JAXBUtils.JSONString2object(readFile(path), o);
		Assert.assertNotNull(b);
	}
	private String readFile( String file ) throws IOException {
	    BufferedReader reader = new BufferedReader( new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line );
	        stringBuilder.append( ls );
	    }

	    return stringBuilder.toString();
	}
}
