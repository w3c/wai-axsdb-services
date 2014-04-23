package org.w3c.wai.accessdb.sync;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.om.SuccessCriterio;
import org.w3c.wai.accessdb.om.SuccessCriterioTechniqueRelation;
import org.w3c.wai.accessdb.om.Technique;
import org.w3c.wai.accessdb.om.WebTechnology;
import org.w3c.wai.accessdb.sync.om.GitHubTechniqueInfo;
import org.w3c.wai.accessdb.sync.om.ImportResponse;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class GitHubTechniqueParser {
	private static final Logger logger = LoggerFactory
			.getLogger(GitHubTechniqueParser.class);

	public static ImportResponse<Technique> importTechnique(
			GitHubTechniqueInfo tinfo) {
		ImportResponse<Technique> result = new ImportResponse<Technique>();
		Technique technique = null;
		try {
			String url = tinfo.getUrl();
			String webTechnologyNameId = tinfo.getWebTechnology().getNameId();
			WebTechnology webTechnology = EAOManager.INSTANCE
					.getWebTechnologyEAO().findByNameId(webTechnologyNameId);
			if (webTechnology == null) {
				webTechnology = EAOManager.INSTANCE.getWebTechnologyEAO()
						.persist(tinfo.getWebTechnology());
			}
			InputSource isTech = new InputSource(url);
			XPath xpath = XPathFactory.newInstance().newXPath();
			Document docTech = null;
			XPathExpression xscriteria = null;
			xscriteria = xpath.compile("//applies-to/success-criterion");
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setValidating(false);
			factory.setFeature(
					"http://apache.org/xml/features/disallow-doctype-decl",
					false); // ignore
							// dtd
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
			Element techniqueE = (Element) docTech.getElementsByTagName(
					"technique").item(0);
			String techniqueId = techniqueE.getAttribute("id");
			boolean exists = false;
			technique = EAOManager.INSTANCE.getTechniqueEAO().findByNameId(
					techniqueId);
			if (technique != null)
				exists = true;
			if (!exists)
				technique = new Technique();
			technique.setNameId(techniqueId);
			technique.setSpecRef(url);
			technique.setTitle(techniqueE.getElementsByTagName("short-name")
					.item(0).getTextContent().trim()
					.replaceAll("\\r\\n|\\r|\\n", " "));
			technique.setWebTechnology(webTechnology);
			technique.setSha(tinfo.getSha());
			technique.setLastModified(tinfo.getDate());
			technique = EAOManager.INSTANCE.getTechniqueEAO()
					.persist(technique);
			NodeList criteria = (NodeList) xscriteria.evaluate(techniqueE,
					XPathConstants.NODESET);
			logger.debug("tech: " + technique.getNameId());
			// sc applies
			for (int k = 0; k < criteria.getLength(); k++) {
				if(exists){
					List<SuccessCriterioTechniqueRelation> rels = EAOManager.INSTANCE.getCriterioTechniqueRelationEAO().findByTechniqueNameId(techniqueId);
					for (SuccessCriterioTechniqueRelation scr : rels) {
						EAOManager.INSTANCE.getCriterioTechniqueRelationEAO().remove(scr);
					}
				}
				Element appliesToE = (Element) criteria.item(k);
				// get sc and same relation to technique
				String idRef = appliesToE.getAttribute("idref");
				String relationship = appliesToE.getAttribute("relationship");
				SuccessCriterio sc = EAOManager.INSTANCE.getCriterioEAO()
						.getByIdRef(idRef);
				if (sc != null) {
					sc.getTechniques().add(technique);
					sc = EAOManager.INSTANCE.getCriterioEAO().persist(sc);
					SuccessCriterioTechniqueRelation rel = new SuccessCriterioTechniqueRelation();
					rel.setSuccessCriterio(sc);
					rel.setTechnique(technique);
					rel.setTechniqueRole(relationship);
					EAOManager.INSTANCE.getCriterioTechniqueRelationEAO()
							.persist(rel);
					logger.debug("adding SuccessCriterioTechniqueRelation "
							+ rel);

				} else
					logger.error("sc not found!!! : " + idRef);
				result.setEntity(technique);
				result.setStatusCode(ImportResponse.OK);
			}
		} catch (Exception e) {
			result.setEntity(technique);
			result.setStatusCode(ImportResponse.FAIL);
		}
		return result;
	}
}
