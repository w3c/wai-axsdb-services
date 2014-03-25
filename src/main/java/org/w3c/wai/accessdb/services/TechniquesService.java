/**
 * 
 */
package org.w3c.wai.accessdb.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.eao.TechniqueEAO;
import org.w3c.wai.accessdb.eao.WebTechnologyEAO;
import org.w3c.wai.accessdb.jaxb.TreeNodeData;
import org.w3c.wai.accessdb.om.SuccessCriterio;
import org.w3c.wai.accessdb.om.SuccessCriterioTechniqueRelation;
import org.w3c.wai.accessdb.om.Technique;
import org.w3c.wai.accessdb.om.WebTechnology;
import org.w3c.wai.accessdb.om.testunit.TestUnitDescription;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;

/**
 * @author evangelos.vlachogiannis
 * @since 03.05.12
 */

public enum TechniquesService {
	INSTANCE;
	private static final Logger logger = LoggerFactory
			.getLogger(TechniquesService.class);

	public Technique deleteTestsAndResultsByTechniqueNameId(String nameId)
			throws ASBPersistenceException {
		Technique technique = null;
		try {
			technique = EAOManager.INSTANCE.getTechniqueEAO()
					.getRequirementsByTerm(nameId).get(0);
			List<TestUnitDescription> tests = EAOManager.INSTANCE
					.getTestUnitDescriptionEAO().findByTechnique(nameId);
			for (TestUnitDescription test : tests) {
				TestsService.INSTANCE.deleteDeepTestUnitById(test
						.getTestUnitId());
			}
			List<SuccessCriterio> criteria = EAOManager.INSTANCE
					.getCriterioEAO().findByTechniqueNameId(technique.getNameId());
			for (SuccessCriterio criterio : criteria) {
				List<Technique> ts = criterio.getTechniques();
				List<Technique> copyList = new ArrayList<Technique>(ts);
				Iterator<Technique> i = copyList.iterator();
				while (i.hasNext()) {
					Technique t = (Technique) i.next();
					if (t.getId() == technique.getId()) {
						ts.clear();
						ts.remove(t);
						ts.clear();
					}
				}
				criterio.setTechniques(ts);
				EAOManager.INSTANCE.getCriterioEAO().persist(criterio);
			}
			List<SuccessCriterioTechniqueRelation> rs = EAOManager.INSTANCE
					.getCriterioTechniqueRelationEAO().findByTechniqueNameId(
							technique.getNameId());
			for (SuccessCriterioTechniqueRelation successCriterioTechniqueRelation : rs) {
				EAOManager.INSTANCE.getCriterioTechniqueRelationEAO().delete(
						successCriterioTechniqueRelation);
			}
			// del the relationships
			EAOManager.INSTANCE.getTechniqueEAO().delete(technique);
		} catch (Exception e) {
			throw new ASBPersistenceException(e);
		}
		return technique;

	}

	public boolean deleteTechniquesBySpec(long webTechId) {
		WebTechnology tech = EAOManager.INSTANCE.getWebTechnologyEAO()
				.findById(webTechId);
		try {
			EAOManager.INSTANCE.getWebTechnologyEAO().delete(tech);
		} catch (ASBPersistenceException e) {
			return false;
		}
		return true;
	}

	public TreeNodeData getWebTechnologiesTree() {
		TreeNodeData rootNode = new TreeNodeData();
		List<WebTechnology> technologies = EAOManager.INSTANCE
				.getWebTechnologyEAO().findAll();
		logger.info("getWebTechnologyEAO : " + technologies.size());
		for (WebTechnology webTechnology : technologies) {
			TreeNodeData node = new TreeNodeData();
			node.setType(WebTechnology.class.getSimpleName());
			node.setId(String.valueOf(webTechnology.getId()));
			node.setLabel(webTechnology.getNameId());
			node.setValue(webTechnology.getNameId());
			node.setDescription(webTechnology.getReqRefDoc());
			node.setNoOfChildren(0);
			node.setSubselector(false);
			rootNode.getChildren().add(node);
		}
		return rootNode;
	}

	public TreeNodeData getWebTechnologiesWithTechniquesTree() {
		TreeNodeData rootNode = new TreeNodeData();
		List<WebTechnology> technologies = EAOManager.INSTANCE
				.getWebTechnologyEAO().findAll();
		logger.info("getWebTechnologyEAO : " + technologies.size());
		for (WebTechnology webTechnology : technologies) {
			TreeNodeData node = new TreeNodeData();
			node.setType(WebTechnology.class.getSimpleName());
			node.setId(String.valueOf(webTechnology.getId()));
			node.setLabel(webTechnology.getNameId());
			node.setValue(webTechnology.getNameId());
			node.setDescription(webTechnology.getReqRefDoc());
			node.setNoOfChildren(0);
			node.setSubselector(true);
			List<Technique> techniques = EAOManager.INSTANCE.getTechniqueEAO()
					.findByWebTechNameId(webTechnology.getNameId());
			for (Technique technique : techniques) {
				TreeNodeData nodeT = new TreeNodeData();
				nodeT.setType(Technique.class.getSimpleName());
				nodeT.setId(String.valueOf(technique.getId()));
				nodeT.setLabel(technique.getNameId() + " "
						+ technique.getTitle());
				nodeT.setValue(technique.getNameId());
				long noOfResults = EAOManager.INSTANCE.getTestResultEAO()
						.findByTechniqueNameId(technique.getNameId()).size();
				if (noOfResults > 0)
					nodeT.setDisabled(false);
				else
					nodeT.setDisabled(true);
				nodeT.setNoOfChildren((int) noOfResults);
				nodeT.setSubselector(false);
				node.getChildren().add(nodeT);
			}
			rootNode.getChildren().add(node);
		}
		return rootNode;
	}

	public List<Technique> getTechniquesByTerm(String term) {
		TechniqueEAO eao = new TechniqueEAO();
		return eao.getRequirementsByTerm(term);
	}

	public List<Technique> retrieveTechniques(String type)
			throws ASBPersistenceException {
		WebTechnologyEAO eao = new WebTechnologyEAO();
		WebTechnology rType = eao.findByNameId(type);
		return EAOManager.INSTANCE.getTechniqueEAO().findByWebTechNameId(
				rType.getNameId());
	}
}
