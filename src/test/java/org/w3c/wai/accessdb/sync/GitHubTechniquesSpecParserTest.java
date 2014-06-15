package org.w3c.wai.accessdb.sync;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import junit.framework.Assert;

import org.junit.Test;
import org.w3c.wai.accessdb.om.Technique;
import org.w3c.wai.accessdb.sync.om.GitHubTechniqueInfo;
import org.w3c.wai.accessdb.sync.om.ImportResponse;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;
import org.xml.sax.SAXException;

public class GitHubTechniquesSpecParserTest {

	@Test 
	public void test() throws XPathExpressionException, ParserConfigurationException, ASBPersistenceException, IOException, SAXException {
		String url = "https://raw.github.com/w3c/wcag/Working-Branch-for-Fall-2014/wcag20/sources/techniques/aria.xml";
		ImportResponse<List<GitHubTechniqueInfo>> allInGit = GitHubTechniquesSpecParser.prepareImport(url);
		System.out.println("all : " + allInGit.getEntity().size());
		List<ImportResponse<GitHubTechniqueInfo>> filtered = GitHubTechniquesSpecParser.filterTechniques(allInGit);
		System.out.println("filtered : " + filtered.size());
		//  sent to the user
		
		// the user makes selections

		for (ImportResponse<GitHubTechniqueInfo> ir : filtered) {
			System.out.println("tech info found : " + ir);
		}
		List<ImportResponse<Technique>> rs = GitHubTechniquesSpecParser.importTechniques(filtered);
		System.out.println(rs);
		Assert.assertEquals(rs.size()>0, true);
	}

}
