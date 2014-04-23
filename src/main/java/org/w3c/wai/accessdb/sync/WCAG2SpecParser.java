package org.w3c.wai.accessdb.sync;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.om.Guideline;
import org.w3c.wai.accessdb.om.Principle;
import org.w3c.wai.accessdb.om.SuccessCriterio;
import org.xml.sax.InputSource;

public class WCAG2SpecParser {

	private static final Logger LOG = LoggerFactory
			.getLogger(WCAG2SpecParser.class);

	public static void parse(String url) throws Exception {
		InputSource is = new InputSource(url);
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression xprinciples = null;
		XPathExpression xguidelines = null;
		XPathExpression xscriteria = null;
		Document doc = null;
		xprinciples = xpath.compile("//div2[@role='principle']");
		xguidelines = xpath.compile("./div3[@role='group1']");
		xscriteria = xpath
				.compile("./div4[@role='req' or @role='bp' or @role='additional']/div5[@role='sc']");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(false);
		factory.setXIncludeAware(true);
		DocumentBuilder parser = factory.newDocumentBuilder();
		doc = parser.parse(is);
		NodeList principles = (NodeList) xprinciples.evaluate(doc,
				XPathConstants.NODESET);
		for (int i = 0; i < principles.getLength(); i++) {
			Element pE = (Element) principles.item(i);
			Principle principle = new Principle();
			principle.setIdRef(pE.getAttribute("id"));
			principle.setNumber(String.valueOf(i + 1));
			principle.setTitle(pE.getChildNodes().item(1).getTextContent()
					.trim());
			LOG.info("Principle " + principle.getNumber());
			principle = (Principle) EAOManager.INSTANCE.getObjectEAO().persist(
					principle);
			NodeList guidelines = (NodeList) xguidelines.evaluate(pE,
					XPathConstants.NODESET);
			for (int j = 0; j < guidelines.getLength(); j++) {
				Element gE = (Element) guidelines.item(j);
				Guideline guideline = new Guideline();
				guideline.setIdRef(gE.getAttribute("id"));
				guideline.setNumber(String.valueOf(principle.getNumber() + "."
						+ (j + 1)));
				guideline.setTitle(gE.getChildNodes().item(1).getTextContent()
						.trim());
				LOG.info("Guideline " + guideline.getNumber());
				guideline = (Guideline) EAOManager.INSTANCE.getObjectEAO()
						.persist(guideline);
				principle.getGuidelines().add(guideline);
				principle = (Principle) EAOManager.INSTANCE.getObjectEAO()
						.persist(principle);
				NodeList scriteria = (NodeList) xscriteria.evaluate(gE,
						XPathConstants.NODESET);
				for (int k = 0; k < scriteria.getLength(); k++) {
					Element sE = (Element) scriteria.item(k);
					String level = getLevel(sE);
					SuccessCriterio sc = new SuccessCriterio();
					sc.setIdRef(sE.getAttribute("id"));
					sc.setNumber(String.valueOf(guideline.getNumber() + "."
							+ (k + 1)));
					sc.setTitle(sE.getChildNodes().item(1).getTextContent()
							.trim());
					sc.setLevel(level);
					LOG.info("SuccessCriterio " + sc.getNumber() + " " + level);
					sc = (SuccessCriterio) EAOManager.INSTANCE.getObjectEAO()
							.persist(sc);
					guideline.getCriterios().add(sc);
					guideline = (Guideline) EAOManager.INSTANCE.getObjectEAO()
							.persist(guideline);
				}
			}
		}
	}
	private static String getLevel(Node node) {
		String l = ((Element) node.getParentNode()).getAttribute("role");
		if (l.equals("req"))
			return "A";
		if (l.equals("bp"))
			return "AA";
		if (l.equals("additional"))
			return "AAA";
		return null;
	}
}
