package org.w3c.wai.accessdb.sync;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
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
import org.w3c.wai.accessdb.utils.ASBPersistenceException;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TechniquesSpecParser {
	private static final Logger LOG = LoggerFactory
			.getLogger(TechniquesSpecParser.class);
@Deprecated
	public static List<Technique> parse(String url) throws XPathExpressionException, ParserConfigurationException, ASBPersistenceException, IOException, SAXException {
		List<Technique> newTechniques = new ArrayList<Technique>();
		InputSource isTech = new InputSource(url);
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression xscriteria = null;
		XPathExpression xtechniques = null;
		Document docTech = null;
		xscriteria = xpath.compile("//applies-to/success-criterion");
		xtechniques = xpath.compile("//body/div1/technique");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setXIncludeAware(true);
		factory.setFeature(
				"http://apache.org/xml/features/disallow-doctype-decl",
				false); // ignore dtd
		DocumentBuilder parser = factory.newDocumentBuilder();
		parser.setEntityResolver(new EntityResolver() {
	        @Override
	        public InputSource resolveEntity(String publicId, String systemId)
	                throws SAXException, IOException {
	            if (systemId.contains(".dtd")) {
	                return new InputSource(new StringReader(""));
	            } else {
	                return null;
	            }
	        }
	    });
		docTech = parser.parse(isTech);
		LOG.debug("parsed");
		Element headerE = (Element) docTech.getElementsByTagName("header")
				.item(0);
		String techNameId = headerE.getElementsByTagName("w3c-designation")
				.item(0).getTextContent();
		WebTechnology webTechnology = EAOManager.INSTANCE.getWebTechnologyEAO()
				.findByNameId(techNameId);
		if (webTechnology == null) {
			webTechnology = new WebTechnology();
			webTechnology.setName(headerE.getElementsByTagName("title").item(0)
					.getTextContent());
			webTechnology.setNameId(techNameId);
			webTechnology.setPath(url);
			webTechnology.setReqRefDoc(headerE.getElementsByTagName("publoc")
					.item(0).getTextContent().trim());
			webTechnology = EAOManager.INSTANCE.getWebTechnologyEAO().persist(
					webTechnology);
		}
		NodeList techniques = (NodeList) xtechniques.evaluate(docTech,
				XPathConstants.NODESET);
		for (int i = 0; i < techniques.getLength(); i++) {
			Element techniqueE = (Element) techniques.item(i);
			String techniqueId = techniqueE.getAttribute("id");
			Technique technique = EAOManager.INSTANCE.getTechniqueEAO()
					.findByNameId(techniqueId);
			if (technique == null) {
				technique = new Technique();
				technique.setNameId(techniqueId);
				technique.setSpecRef(url);
				technique.setTitle(techniqueE
						.getElementsByTagName("short-name").item(0)
						.getTextContent().trim()
						.replaceAll("\\r\\n|\\r|\\n", " "));
				technique.setWebTechnology(webTechnology);
				technique = EAOManager.INSTANCE.getTechniqueEAO().persist(
						technique);
				NodeList criteria = (NodeList) xscriteria.evaluate(techniqueE,
						XPathConstants.NODESET);
				LOG.info("tech: " + technique.getNameId());

				// sc applies
				for (int k = 0; k < criteria.getLength(); k++) {
					Element appliesToE = (Element) criteria.item(k);
					// get sc and same relation to technique
					String idRef = appliesToE.getAttribute("idref");
					String relationship = appliesToE
							.getAttribute("relationship");
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
					} else
						LOG.error("sc not found!!! : " + idRef);
				}
				newTechniques.add(technique);
			}
			LOG.info("imported " + newTechniques.size() + " from " + url);

		}
		return newTechniques;
	}
}
