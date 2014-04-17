package org.w3c.wai.accessdb.sync;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.w3c.wai.accessdb.om.Technique;
import org.w3c.wai.accessdb.sync.TechniquesSpecParser;

public class TestTechniquesSpecParser {

	@Test
	public void test() {
		String specURL = "https://raw.github.com/w3c/wcag/master/wcag20/sources/ARIA-tech-src.xml";
		List<Technique> l = new ArrayList<Technique>();
		try{
			l = TechniquesSpecParser.parse(specURL);
			System.out.println(l.size());
		}
		catch(Exception e){
		}
		assertEquals(l.size(),18);

	}

}
