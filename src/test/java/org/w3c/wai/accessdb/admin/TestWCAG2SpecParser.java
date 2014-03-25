package org.w3c.wai.accessdb.admin;

import org.junit.Test;
import org.w3c.wai.accessdb.parsers.WCAG2SpecParser;

public class TestWCAG2SpecParser {

	@Test 
	public void test() {
		String specURL = "http://www.w3.org/WAI/GL/WCAG20/sources/wcag2-src.xml";
		try{
			WCAG2SpecParser.parse(specURL);
			//System.out.println(l.size());
		}
		catch(Exception e){
		}
		//assertEquals(l.size(),18);

	}

}
