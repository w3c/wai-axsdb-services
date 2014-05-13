/**
 * 
 */
package org.w3c.wai.accessdb.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.helpers.TestResultFilterHelper;
import org.w3c.wai.accessdb.jaxb.SimpleProduct;
import org.w3c.wai.accessdb.jaxb.TechnologyCombination;
import org.w3c.wai.accessdb.jaxb.TestResultDataOverview;
import org.w3c.wai.accessdb.jaxb.TestResultFilter;
import org.w3c.wai.accessdb.jaxb.TestResultFullViewTechnique;
import org.w3c.wai.accessdb.jaxb.TestResultFullViewTechniqueCell;
import org.w3c.wai.accessdb.jaxb.TestResultViewData;
import org.w3c.wai.accessdb.jaxb.TreeNodeData;
import org.w3c.wai.accessdb.om.Technique;
import org.w3c.wai.accessdb.om.TestResult;
import org.w3c.wai.accessdb.om.TestResultsBunch;
import org.w3c.wai.accessdb.om.User;
import org.w3c.wai.accessdb.om.product.AssistiveTechnology;
import org.w3c.wai.accessdb.om.product.UAgent;
import org.w3c.wai.accessdb.om.testunit.TestUnitDescription;
import org.w3c.wai.accessdb.sync.om.ExportTestResultsFile;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;
import org.w3c.wai.accessdb.utils.InOutUtils;
import org.w3c.wai.accessdb.utils.JAXBUtils;

public enum TestResultsService {
	INSTANCE;
	private static final Logger logger = LoggerFactory
			.getLogger(TestResultsService.class);
	private String targetRootExportPath = "/tmp/accessdbexport/";

	public List<TestResultViewData> loadTestResultViewData(
			TestResultFilter filter, String techId) {
		List<TestResultViewData> dataList = new ArrayList<TestResultViewData>();
		List<Object[]> results = null;
		try {
			String q = TestResultFilterHelper.buildHQL4TestResultViewTechnique(
					filter, techId);
			logger.debug("loadTestResultView Query: " + q);
			results = (List<Object[]>) EAOManager.INSTANCE.getTestResultEAO()
					.doSimpleQuery(q);
			logger.debug("loadTestResultView results: " + results.size());

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		for (Object[] result : results) {
			TestResultViewData data = new TestResultViewData(result, techId);
			dataList.add(data);
		}
		return dataList;
	}

	public TestResultFullViewTechnique loadTestResultFullViewTechnique(
			TestResultFilter filter, String techId) {
		// find unique combinations based on filter and technique
		TestResultFullViewTechnique view = new TestResultFullViewTechnique();
		view.setTechniqueNameId(techId);
		List<Object[]> atCombinations = null;
		try {
			String q = TestResultFilterHelper
					.buildHQL4TestResultFullViewTechnique(filter, techId);
			logger.info("loadTestResultFullViewTechnique Query: " + q);
			atCombinations = (List<Object[]>) EAOManager.INSTANCE
					.getTestResultEAO().doSimpleQuery(q);
		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage());
		}
		logger.debug("getTestResultFullViewTechnique AT combinations: "
				+ atCombinations.size());
		// get Unique Agents and AT
		HashMap<String, UAgent> agentsMap = new HashMap<String, UAgent>();
		HashMap<String, AssistiveTechnology> atMap = new HashMap<String, AssistiveTechnology>();
		for (Object[] combO : atCombinations) {
			TechnologyCombination comb = new TechnologyCombination(combO);
			UAgent uAgent = new UAgent(comb.getUaName(), comb.getUaVersion());
			agentsMap.put(uAgent.toString(), uAgent);
			AssistiveTechnology at = new AssistiveTechnology(comb.getAtName(),
					comb.getAtVersion());
			atMap.put(at.toString(), at);
		}
		logger.debug("found unique UA combinations: " + agentsMap.size());
		logger.debug("found unique AT combinations: " + atMap.size());
		// for each comb get noOfPass and noOf Fail and build the table
		List<TestResultFullViewTechniqueCell> firstRow = new ArrayList<TestResultFullViewTechniqueCell>();
		TestResultFullViewTechniqueCell empty = new TestResultFullViewTechniqueCell();
		empty.setType(TestResultFullViewTechniqueCell.TYPE_HEADER);
		firstRow.add(empty);
		for (String agentS : agentsMap.keySet()) {
			TestResultFullViewTechniqueCell agentCell = new TestResultFullViewTechniqueCell();
			agentCell.setType(TestResultFullViewTechniqueCell.TYPE_HEADER);
			agentCell.setProduct(agentsMap.get(agentS));
			firstRow.add(agentCell);
		}
		logger.debug("firstTableRow: " + firstRow);
		view.getRows().add(firstRow);
		for (String atS : atMap.keySet()) {
			List<TestResultFullViewTechniqueCell> row = new ArrayList<TestResultFullViewTechniqueCell>();
			TestResultFullViewTechniqueCell atCell = new TestResultFullViewTechniqueCell();
			atCell.setType(TestResultFullViewTechniqueCell.TYPE_HEADER);
			atCell.setProduct(atMap.get(atS));
			row.add(atCell);
			for (String agentS : agentsMap.keySet()) {
				TestResultFullViewTechniqueCell agentCell = new TestResultFullViewTechniqueCell();
				agentCell.setType(TestResultFullViewTechniqueCell.TYPE_DATA);
				filter = new TestResultFilter();
				filter.getAts().add(new SimpleProduct(atMap.get(atS)));
				filter.getUas().add(new SimpleProduct(agentsMap.get(agentS)));
				String noOfAll = String.valueOf(EAOManager.INSTANCE
						.getTestResultEAO()
						.doSimpleQuery(
								TestResultFilterHelper
										.buildHQL4TestResultsOverviewAll(
												filter, techId)).get(0));
				String noOfPass = String.valueOf(EAOManager.INSTANCE
						.getTestResultEAO()
						.doSimpleQuery(
								TestResultFilterHelper
										.buildHQL4TestResultsOverviewPass(
												filter, techId)).get(0));
				agentCell.setNoOfAll(noOfAll);
				agentCell.setNoOfPass(noOfPass);
				row.add(agentCell);
			}
			logger.debug("tableRow: " + row);
			view.getRows().add(row);
		}
		logger.debug("tableView: " + view.toString());
		return view;
	}

	public List<TestResultDataOverview> loadTestResultDataOverview(
			TestResultFilter filter) {
		List<TestResultDataOverview> results = new ArrayList<TestResultDataOverview>();
		String q = TestResultFilterHelper.buildHQL4Technique(filter);
		logger.debug(q);
		List<Technique> techniques = EAOManager.INSTANCE.getTechniqueEAO()
				.doSimpleQuery(q);
		for (Technique technique : techniques) {
			String namedId = technique.getNameId();
			TestResultDataOverview res = new TestResultDataOverview();
			res.setTechnique(namedId);
			String noOfAll = String.valueOf(EAOManager.INSTANCE
					.getTestResultEAO()
					.doSimpleQuery(
							TestResultFilterHelper
									.buildHQL4TestResultsOverviewAll(filter,
											namedId)).get(0));
			String noOfPass = String.valueOf(EAOManager.INSTANCE
					.getTestResultEAO()
					.doSimpleQuery(
							TestResultFilterHelper
									.buildHQL4TestResultsOverviewPass(filter,
											namedId)).get(0));
			res.setNoOfAll(noOfAll);
			res.setNoOfPass(noOfPass);
			res.setTechniqueTitle(technique.getTitle());
			/*
			res.setNoOfUniqueCombinations(EAOManager.INSTANCE
					.getTestResultEAO().countUniqueATCombinationsByTechnique(
							namedId));
			res.setNoOfUniqueContributors(EAOManager.INSTANCE
					.getTestResultEAO().countUniqueContributorsByTechnique(
							namedId));
							*/
			results.add(res);
			logger.debug(res.toString());
		}
		return results;
	}

	public TreeNodeData getResultsNode(TestResultFilter filter) {
		TreeNodeData rootNode = new TreeNodeData();
		rootNode.setType("ROOT");

		List<TestResult> results = new ArrayList<TestResult>();
		try {
			String q = TestResultFilterHelper.buildHQL4TestResults(filter);
			logger.info("getResultsNode Query: " + q);
			results = EAOManager.INSTANCE.getTestResultEAO().doSimpleQuery(q);
		} catch (Exception e) {
			logger.debug(e.getLocalizedMessage());
		}
		logger.info("getResultsNode combinations from database: "
				+ results.size());
		return rootNode;
	}

	public TestResultsBunch saveResultsBunch(TestResultsBunch testResultsBunch)
			throws ASBPersistenceException {
		logger.info("saveResultsBunch with no of results:"
				+ testResultsBunch.getResults().size());
		try {
			String userId = testResultsBunch.getUser().getUserId();
			User user = EAOManager.INSTANCE.getUserEAO().findByUserId(userId);
			testResultsBunch.setUser(user);
			testResultsBunch.setDate(new Date());
			testResultsBunch = (TestResultsBunch) EAOManager.INSTANCE
					.getTestResultsBunchEAO().persist(testResultsBunch);
			logger.info("saved bunch id: " + testResultsBunch.getId());
		} catch (ASBPersistenceException e) {
			logger.error("failed to save bunch id: " + testResultsBunch.getId());
			e.printStackTrace();
			throw new ASBPersistenceException(e);
		}
		return testResultsBunch;
	}

	public void deleteTestResultsByTestNameId(String testNameId)
			throws ASBPersistenceException {
		TestUnitDescription test = EAOManager.INSTANCE
				.getTestUnitDescriptionEAO().findByTestUnitId(testNameId);
		List<TestResult> results = EAOManager.INSTANCE.getTestResultEAO()
				.findByTestUnitId(test.getTestUnitId());
		List<TestResult> copyList = new ArrayList<TestResult>(results);
		Iterator<TestResult> i = copyList.iterator();
		while (i.hasNext()) {
			TestResult testResult = (TestResult) i.next();
			TestResultsBunch b = EAOManager.INSTANCE.getTestResultsBunchEAO()
					.findbyTestResultId(testResult.getId());
			List<TestResult> rs = b.getResults();
			for (TestResult r : rs) {
				if (r.getId() == testResult.getId()) {
					results.clear();
					results.remove(r);
					results.clear();
				}
			}
			b.setResults(results);
			EAOManager.INSTANCE.getTestResultsBunchEAO().persist(b);
			EAOManager.INSTANCE.getTestResultEAO().delete(testResult);
		}
		
	}
	
	public void exportAll() {
		targetRootExportPath = ConfigService.INSTANCE
				.getConfigParam("targetRootExportPath");
		logger.info("Taking config param targetRootExportPath="
				+ targetRootExportPath);
		Date date = new Date();
		ExportTestResultsFile indexFile = new ExportTestResultsFile();
		indexFile.setCreated(date);
		String exportId = "accessdbTestResultsDataExport" + date.getTime();
		String targetExportPath = targetRootExportPath + exportId;
		File folder = null;
		try {
			folder = InOutUtils.createFolder(targetExportPath);
			logger.info("Created export folder: " + targetExportPath);
		} catch (Exception e) {
			logger.error("Cannot create export folder: " + targetExportPath);
			logger.error(e.getLocalizedMessage());
		}
		InOutUtils.deleteFolderContents(folder);
		List<TestResult> testResults = EAOManager.INSTANCE.getTestResultEAO().findAll();
		indexFile.settestResults(testResults);
		try {
			JAXBUtils.objectToXmlFile(targetExportPath + "/testresults.xml",
					indexFile);
			InOutUtils.zip(new File(targetExportPath), new File(
					targetRootExportPath + "/" + exportId + ".zip"));
		} catch (IOException e) {
			logger.error("Error while writing export index file");
			logger.debug(e.getLocalizedMessage());
		} catch (JAXBException e) {
			logger.error("Error while writing export index file");
			logger.debug(e.getLocalizedMessage());
		}
	}

}
