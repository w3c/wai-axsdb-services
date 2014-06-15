package org.w3c.wai.accessdb.sync;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.om.Technique;
import org.w3c.wai.accessdb.om.WebTechnology;
import org.w3c.wai.accessdb.sync.om.GitHubTechniqueInfo;
import org.w3c.wai.accessdb.sync.om.ImportResponse;
import org.w3c.wai.accessdb.utils.StringUtils;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class GitHubTechniquesSpecParser {
	private static final Logger logger = LoggerFactory
			.getLogger(GitHubTechniquesSpecParser.class);

	/**
	 * Parses the web technology (e.g. ARIA) XML and collects info about
	 * Asssumptions: let url the techniques url provided by the user e.g
	 * https://
	 * github.com/w3c/wcag/blob/Working-Branch-for-Fall-2014/wcag20/sources
	 * /techniques/aria.xml
	 * 
	 * @param urlS
	 * @return
	 * @throws IOException 
	 */
	public static ImportResponse<List<GitHubTechniqueInfo>> prepareImport(
			String urlS) throws IOException {
		String branch = urlS.split("/")[5];
		GitHubService.INSTANCE.init(branch);
		ImportResponse<List<GitHubTechniqueInfo>> result = new ImportResponse<List<GitHubTechniqueInfo>>();
		List<GitHubTechniqueInfo> infos = new ArrayList<GitHubTechniqueInfo>();
		try {
			URL url = new URL(urlS);
			String parentPath = StringUtils.getParentPath(url.toString());
			InputSource isTech = new InputSource(url.openStream());
			Document docTech = null;
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setValidating(false);
			factory.setXIncludeAware(false);
			// ignore dtd
			factory.setFeature(
					"http://apache.org/xml/features/disallow-doctype-decl",
					false);
			factory.setFeature("http://apache.org/xml/features/xinclude", false);
			DocumentBuilder parser = factory.newDocumentBuilder();
			parser.setEntityResolver(new EntityResolver() {
				@Override
				public InputSource resolveEntity(String publicId,
						String systemId) throws SAXException, IOException {
					if (systemId.contains(".dtd")) {
						return new InputSource(new StringReader(""));
					} else {
						return null;
					}
				}
			});
			docTech = parser.parse(isTech);
			logger.debug("parsed");
			Element headerE = (Element) docTech.getElementsByTagName("header")
					.item(0);
			String techNameId = headerE.getElementsByTagName("w3c-designation")
					.item(0).getTextContent();
			WebTechnology webTechnology = new WebTechnology();
			webTechnology.setName(headerE.getElementsByTagName("title").item(0)
					.getTextContent());
			webTechnology.setNameId(techNameId);
			webTechnology.setPath(urlS);
			webTechnology.setReqRefDoc(headerE.getElementsByTagName("publoc")
					.item(0).getTextContent().trim());
			NodeList techniquesDocs = docTech
					.getElementsByTagName("xi:include");
			String fullHref = null;
			for (int i = 0; i < techniquesDocs.getLength(); i++) {
				Element e = (Element) techniquesDocs.item(i);
				String href = e.getAttribute("href");
				fullHref = parentPath + "/" + href;
				logger.debug("for technique url: " + href);
				GitHubTechniqueInfo info = GitHubService.INSTANCE.getTechniqueGitMeta(href);
				info.setUrl(fullHref);
				info.setTechnique(FilenameUtils.getBaseName(href));
				info.setWebTechnology(webTechnology);
				infos.add(info);
			}
			result.setEntity(infos);
			result.setStatusCode(ImportResponse.OK);
		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage());
			result.setEntity(infos);
			result.setStatusCode(ImportResponse.FAIL);
		}
		return result;
	}
	
	public static List<ImportResponse<GitHubTechniqueInfo>> filterTechniques(
			ImportResponse<List<GitHubTechniqueInfo>> response) {
		List<ImportResponse<GitHubTechniqueInfo>> results = new ArrayList<ImportResponse<GitHubTechniqueInfo>>();
		if (response == null || response.getEntity().size() < 1)
			return results;
		WebTechnology webTechnology = response.getEntity().get(0)
				.getWebTechnology();
		// for every technique in DB 
		List<Technique> allWebTechTechniques = EAOManager.INSTANCE
				.getTechniqueEAO().findByWebTechNameId(
						webTechnology.getNameId());
		for (Technique techniqueInDb : allWebTechTechniques) {
			GitHubTechniqueInfo tmpInfo = new GitHubTechniqueInfo(techniqueInDb.getNameId());
			if(!response.getEntity().contains(tmpInfo)){
				ImportResponse<GitHubTechniqueInfo> res = new ImportResponse<GitHubTechniqueInfo>();
				res.setStatusCode(ImportResponse.ONLY_IN_DB);
				tmpInfo.setWebTechnology(webTechnology);
				res.setEntity(tmpInfo);
				results.add(res);
			}
		}

		for (GitHubTechniqueInfo tinfo : response.getEntity()) {
			ImportResponse<GitHubTechniqueInfo> res = new ImportResponse<GitHubTechniqueInfo>();
			Technique inDbTechnique = EAOManager.INSTANCE.getTechniqueEAO()
					.findByNameId(tinfo.getTechnique());
			if (inDbTechnique == null) {
				res.setEntity(tinfo);
				res.setStatusCode(ImportResponse.ONLY_IN_WCAG);
				results.add(res);
			} else {
				if(inDbTechnique.getSha()==null)
					inDbTechnique.setSha("dummy");
				if (inDbTechnique.getSha().equals(tinfo.getSha())) {
					res.setStatusCode(ImportResponse.SAME);
				} else {
					if (inDbTechnique.getLastModified()!=null && inDbTechnique.getLastModified().before(tinfo.getDate())) {
						res.setStatusCode(ImportResponse.NEWER);
					} else {
						res.setStatusCode(ImportResponse.NOTDEFINED);
					}
				}
				res.setEntity(tinfo);
				results.add(res);
			}
		}
		return results;
	}

	public static List<ImportResponse<Technique>> importTechniques(
			List<ImportResponse<GitHubTechniqueInfo>> responses) {
		List<ImportResponse<Technique>> results = new ArrayList<ImportResponse<Technique>>();
		for (ImportResponse<GitHubTechniqueInfo> res : responses) {
			ImportResponse<Technique> r = GitHubTechniqueParser
					.importTechnique(res.getEntity());
			results.add(r);
		}
		return results;
	}	
	
	public static List<ImportResponse<Technique>> importTechniques(String url) throws IOException {
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
		logger.info(rs.size()+" done");
		return rs;
	}	
	
}