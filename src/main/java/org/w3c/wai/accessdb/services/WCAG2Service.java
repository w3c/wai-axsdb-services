/**
 * 
 */
package org.w3c.wai.accessdb.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.jaxb.TreeNodeData;
import org.w3c.wai.accessdb.om.Guideline;
import org.w3c.wai.accessdb.om.Principle;
import org.w3c.wai.accessdb.om.SuccessCriterio;
import org.w3c.wai.accessdb.om.Technique;
import org.w3c.wai.accessdb.om.WebTechnology;

/**
 * @author evangelos.vlachogiannis
 */

public enum WCAG2Service {
	INSTANCE;
	private static final Logger logger = LoggerFactory
			.getLogger(WCAG2Service.class);

	public TreeNodeData getSimpleWCAGTreeData(String level) {
		TreeNodeData rootNode = new TreeNodeData();
		logger.info("getting WCAG with level: " + level);
		int wcagLevel = 0;
		if (level.equals("A"))
			wcagLevel = 1;
		else if (level.equals("AAA"))
			wcagLevel = 3;
		else
			wcagLevel = 2;
		List<Principle> principles = EAOManager.INSTANCE.getPrincipleEAO()
				.findAll();
		rootNode.setNoOfChildren(principles.size());
		rootNode.setSubselector(true);
		for (Principle principle : principles) {
			TreeNodeData nodePrinciple = new TreeNodeData();
			nodePrinciple.setType(Principle.class.getSimpleName());
			nodePrinciple.setId(String.valueOf(principle.getId()));
			nodePrinciple.setLabel(principle.getNumber());
			nodePrinciple.setValue(String.valueOf(principle.getNumber()));
			nodePrinciple.setDescription(principle.getTitle());
			List<Guideline> guidelines = principle.getGuidelines();
			nodePrinciple.setNoOfChildren(guidelines.size());
			nodePrinciple.setSubselector(true);
			for (Guideline guideline : guidelines) {
				TreeNodeData nodeGuideline = new TreeNodeData();
				nodeGuideline.setType(Guideline.class.getSimpleName());
				nodeGuideline.setId(String.valueOf(guideline.getId()));
				nodeGuideline.setLabel(guideline.getNumber());
				nodeGuideline.setValue(String.valueOf(guideline.getNumber()));
				nodeGuideline.setDescription(guideline.getTitle());
				List<SuccessCriterio> criterios = guideline.getCriterios();
				nodeGuideline.setNoOfChildren(criterios.size());
				nodeGuideline.setSubselector(true);
				for (SuccessCriterio successCriterio : criterios) {
					TreeNodeData nodeCriterio = new TreeNodeData();
					nodeCriterio.setType(SuccessCriterio.class.getSimpleName());
					nodeCriterio.setDescription(successCriterio.getTitle());
					nodeCriterio.setId(String.valueOf(successCriterio.getId()));
					nodeCriterio.setLabel(successCriterio.getNumber());
					nodeCriterio.setValue(String.valueOf(successCriterio
							.getNumber()));
					nodeCriterio.setSubselector(true);
					if (successCriterio.getLevel().length() > wcagLevel) {
						nodeCriterio.setDisabled(true);
					}
					nodeGuideline.getChildren().add(nodeCriterio);

				}
				nodePrinciple.getChildren().add(nodeGuideline);
			}
			rootNode.getChildren().add(nodePrinciple);
		}
		return rootNode;
	}

	public TreeNodeData getWCAGTreeData(String level) {
		int wcagLevel = level.trim().length();
		if (wcagLevel < 1)
			wcagLevel = 3;
		TreeNodeData rootNode = new TreeNodeData();
		List<Principle> principles = EAOManager.INSTANCE.getPrincipleEAO()
				.findAll();
		rootNode.setNoOfChildren(principles.size());
		rootNode.setSubselector(true);
		for (Principle principle : principles) {
			TreeNodeData nodePrinciple = new TreeNodeData();
			nodePrinciple.setType(Principle.class.getSimpleName());
			nodePrinciple.setId(String.valueOf(principle.getId()));
			nodePrinciple.setLabel(principle.getNumber());
			nodePrinciple.setValue(String.valueOf(principle.getId()));
			nodePrinciple.setDescription(principle.getTitle());
			List<Guideline> guidelines = principle.getGuidelines();
			nodePrinciple.setNoOfChildren(guidelines.size());
			nodePrinciple.setSubselector(true);
			for (Guideline guideline : guidelines) {
				TreeNodeData nodeGuideline = new TreeNodeData();
				nodeGuideline.setType(Guideline.class.getSimpleName());
				nodeGuideline.setId(String.valueOf(guideline.getId()));
				nodeGuideline.setLabel(guideline.getNumber());
				nodeGuideline.setValue(String.valueOf(guideline.getId()));
				nodeGuideline.setDescription(guideline.getTitle());
				List<SuccessCriterio> criterios = guideline.getCriterios();
				nodeGuideline.setNoOfChildren(criterios.size());
				nodeGuideline.setSubselector(true);
				for (SuccessCriterio successCriterio : criterios) {
					if (successCriterio.getLevel().length() >= wcagLevel) {
						TreeNodeData nodeCriterio = new TreeNodeData();
						nodeCriterio.setType(SuccessCriterio.class
								.getSimpleName());
						nodeCriterio.setId(String.valueOf(successCriterio
								.getId()));
						nodeCriterio.setLabel(successCriterio.getNumber());
						nodeCriterio.setValue(String.valueOf(successCriterio
								.getId()));
						List<WebTechnology> technologies = EAOManager.INSTANCE
								.getWebTechnologyEAO().findAll();
						nodeCriterio.setNoOfChildren(technologies.size());
						nodeCriterio.setSubselector(true);
						for (WebTechnology webTechnology : technologies) {
							TreeNodeData nodeTechnology = new TreeNodeData();
							nodeTechnology.setType(WebTechnology.class
									.getSimpleName());
							nodeTechnology.setId(String.valueOf(webTechnology
									.getId()));
							nodeTechnology.setLabel(webTechnology.getNameId());

							List<Technique> techniques = EAOManager.INSTANCE
									.getTechniqueEAO().getRequirementsByTerm(
											webTechnology.getNameId());
							nodeTechnology.setNoOfChildren(techniques.size());
							nodeTechnology.setSubselector(true);
							for (Technique technique : techniques) {
								TreeNodeData nodeTechnique = new TreeNodeData();
								nodeTechnique.setType(Technique.class
										.getSimpleName());
								nodeTechnique.setId(String.valueOf(technique
										.getId()));
								nodeTechnique.setLabel(technique.getNameId());
								nodeTechnique.setDescription(technique
										.getTitle());
								nodeTechnique.setValue(String.valueOf(technique
										.getId()));
								nodeTechnique.setNoOfChildren(0);
								nodeTechnique.setSubselector(false);
								nodeTechnology.getChildren().add(nodeTechnique);
							}
							nodeCriterio.getChildren().add(nodeTechnology);
						}
						nodeGuideline.getChildren().add(nodeCriterio);
					}
				}
				nodePrinciple.getChildren().add(nodeGuideline);
			}
			rootNode.getChildren().add(nodePrinciple);
		}
		return rootNode;
	}
}
