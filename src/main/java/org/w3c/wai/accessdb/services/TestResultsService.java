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
import org.w3c.wai.accessdb.jaxb.TestResultTestOveview;
import org.w3c.wai.accessdb.jaxb.TestResultViewData;
import org.w3c.wai.accessdb.jaxb.TestResultViewTable;
import org.w3c.wai.accessdb.jaxb.TestResultViewTableCell;
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


	public TestResultViewTable loadTestResultFullViewTechnique(
			TestResultFilter filter, String techId) {
		// find unique combinations based on filter and technique
		TestResultViewTable view = new TestResultViewTable();
		view.setId(techId);
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
		List<TestResultViewTableCell> firstRow = new ArrayList<TestResultViewTableCell>();
		TestResultViewTableCell empty = new TestResultViewTableCell();
		empty.setType(TestResultViewTableCell.TYPE_HEADER);
		firstRow.add(empty);
		for (String agentS : agentsMap.keySet()) {
			TestResultViewTableCell agentCell = new TestResultViewTableCell();
			agentCell.setType(TestResultViewTableCell.TYPE_HEADER);
			agentCell.setProduct(agentsMap.get(agentS));
			firstRow.add(agentCell);
		}
		logger.debug("firstTableRow: " + firstRow);
		view.getRows().add(firstRow);
		for (String atS : atMap.keySet()) {
			List<TestResultViewTableCell> row = new ArrayList<TestResultViewTableCell>();
			TestResultViewTableCell atCell = new TestResultViewTableCell();
			atCell.setType(TestResultViewTableCell.TYPE_HEADER);
			atCell.setProduct(atMap.get(atS));
			row.add(atCell);
			for (String agentS : agentsMap.keySet()) {
				TestResultViewTableCell agentCell = new TestResultViewTableCell();
				agentCell.setType(TestResultViewTableCell.TYPE_DATA);
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

	public TestResultViewTable loadTestResultViewTest(
			TestResultFilter filter, String testUnitId) {
		// find unique combinations based on filter and technique
		TestResultViewTable view = new TestResultViewTable();
		view.setId(testUnitId);
		List<Object[]> atCombinations = null;
		try {
			String q = TestResultFilterHelper
					.buildHQL4TestResultViewTest(filter, testUnitId);
			logger.info("loadTestResultViewTable Query: " + q);
			atCombinations = (List<Object[]>) EAOManager.INSTANCE
					.getTestResultEAO().doSimpleQuery(q);
		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage());
		}
		logger.debug("getTestResultViewTable AT combinations: "
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
		List<TestResultViewTableCell> firstRow = new ArrayList<TestResultViewTableCell>();
		TestResultViewTableCell empty = new TestResultViewTableCell();
		empty.setType(TestResultViewTableCell.TYPE_HEADER);
		firstRow.add(empty);
		for (String agentS : agentsMap.keySet()) {
			TestResultViewTableCell agentCell = new TestResultViewTableCell();
			agentCell.setType(TestResultViewTableCell.TYPE_HEADER);
			agentCell.setProduct(agentsMap.get(agentS));
			firstRow.add(agentCell);
		}
		logger.debug("firstTableRow: " + firstRow);
		view.getRows().add(firstRow);
		for (String atS : atMap.keySet()) {
			List<TestResultViewTableCell> row = new ArrayList<TestResultViewTableCell>();
			TestResultViewTableCell atCell = new TestResultViewTableCell();
			atCell.setType(TestResultViewTableCell.TYPE_HEADER);
			atCell.setProduct(atMap.get(atS));
			row.add(atCell);
			for (String agentS : agentsMap.keySet()) {
				TestResultViewTableCell agentCell = new TestResultViewTableCell();
				agentCell.setType(TestResultViewTableCell.TYPE_DATA);
				filter = new TestResultFilter();
				filter.getAts().add(new SimpleProduct(atMap.get(atS)));
				filter.getUas().add(new SimpleProduct(agentsMap.get(agentS)));
				String noOfAll = String.valueOf(EAOManager.INSTANCE
						.getTestResultEAO()
						.doSimpleQuery(
								TestResultFilterHelper
										.buildHQL4CountAllTestResultsOfTestUnit(
												filter, testUnitId)).get(0));
				String noOfPass = String.valueOf(EAOManager.INSTANCE
						.getTestResultEAO()
						.doSimpleQuery(
								TestResultFilterHelper
										.buildHQL4CountPassTestResultsOfTestUnit(
												filter, testUnitId)).get(0));
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

	/**
	 * Get overall results for every technique based on filter. This returns
	 * technique info, pass and fail
	 * 
	 * @param filter
	 * @return
	 */
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
			 * res.setNoOfUniqueCombinations(EAOManager.INSTANCE
			 * .getTestResultEAO().countUniqueATCombinationsByTechnique(
			 * namedId)); res.setNoOfUniqueContributors(EAOManager.INSTANCE
			 * .getTestResultEAO().countUniqueContributorsByTechnique(
			 * namedId));
			 */
			results.add(res);
			logger.debug(res.toString());
		}
		return results;
	}

	public List<TestResultTestOveview> findByFilterTestResultTestOveview(
			TestResultFilter filter, String techniqueNameId) {
		List<TestResultTestOveview> resultsData = new ArrayList<TestResultTestOveview>();
		List<TestUnitDescription> test = EAOManager.INSTANCE
				.getTestUnitDescriptionEAO().findByTechnique(techniqueNameId);
		for (Iterator<TestUnitDescription> iterator = test.iterator(); iterator
				.hasNext();) {
			TestUnitDescription tu = (TestUnitDescription) iterator.next();
			TestResultTestOveview res = new TestResultTestOveview();
			String testUnitId = tu.getTestUnitId();
			String qAll = TestResultFilterHelper
					.buildHQL4CountAllTestResultsOfTestUnit(filter, testUnitId);
			logger.debug(qAll);
			String noOfAll = String.valueOf(EAOManager.INSTANCE
					.getTestResultEAO().doSimpleQuery(qAll).get(0));
			String qPass = TestResultFilterHelper
					.buildHQL4CountPassTestResultsOfTestUnit(filter, testUnitId);
			logger.debug(qAll);
			String noOfPass = String.valueOf(EAOManager.INSTANCE
					.getTestResultEAO().doSimpleQuery(qPass).get(0));
			res.setNoOfAll(noOfAll);
			res.setNoOfPass(noOfPass);
			res.setTestUnitId(testUnitId);
			res.setTestTitle(tu.getTitle());
			resultsData.add(res);
			logger.debug(res.toString());
		}
		return resultsData;
	}

	public List<TestResult> getResults(TestResultFilter filter) {
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
		return results;
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

	public boolean importAllTestResults(String indexFilePath)
			throws ASBPersistenceException {
		try {
			File indexF = new File(indexFilePath);
			ExportTestResultsFile indexFile = new ExportTestResultsFile();
			indexFile = (ExportTestResultsFile) JAXBUtils.fileToObject(indexF,
					ExportTestResultsFile.class);
			if (indexFile == null) {
				logger.error("Cannot load index file for importing");
				return false;
			}
			for (TestResultsBunch bunch : indexFile.getTestResultsBunch()) {
				String userId = bunch.getUser().getUserId();
				TestResultsBunch b = new TestResultsBunch();
				User user = EAOManager.INSTANCE.getUserEAO().findByUserId(
						userId);
				if (user == null) {
					logger.warn("User not found: " + userId);
					user = EAOManager.INSTANCE.getUserEAO()
							.findByUserId("anon");
					logger.warn("Anon user used");
				}
				b.setUser(user);
				b.setDate(bunch.getDate());
				b.setOptionalName(bunch.getOptionalName());
				if (b.getOptionalName() == null) {
					b.setOptionalName("imported");
				}
				List<TestResult> rs = new ArrayList<TestResult>();
				for (TestResult res : bunch.getResults()) {
					TestResult r = new TestResult();
					TestUnitDescription tu = EAOManager.INSTANCE
							.getTestUnitDescriptionEAO().findByTestUnitId(
									res.getTestUnitDescription()
											.getTestUnitId());
					if (tu == null) {
						logger.warn("Test not found in db: "
								+ tu.getTestUnitId());
						continue;
					}
					r.setTestUnitDescription(tu);
					r.setComment(res.getComment());
					r.setResultValue(res.isResultValue());
					r.setRunDate(res.getRunDate());
					r.setTestingProfile(res.getTestingProfile());
					logger.info("Addition result for test: "
							+ r.getTestUnitDescription().getTestUnitId());
					rs.add(r);
				}
				logger.info("Saving...");
				this.saveResultsBunch(b);
				logger.info("Saved successfully...");
			}
			return true;
		} catch (Exception e) {
			logger.error("Unexpect import error: " + e.getLocalizedMessage());
			logger.debug("Unexpect import error: ", e.getStackTrace());
			return false;
		}
	}

	public void exportAllTestResults() {
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
		List<TestResultsBunch> bunches = EAOManager.INSTANCE
				.getTestResultsBunchEAO().findAll();
		logger.info("no of bunchs " + bunches.size());
		for (Iterator<TestResultsBunch> iterator = bunches.iterator(); iterator
				.hasNext();) {
			TestResultsBunch testResultsBunch = (TestResultsBunch) iterator
					.next();
			List<TestResult> r = testResultsBunch.getResults();
			logger.debug("Results for " + testResultsBunch.getId() + " "
					+ r.size());
			indexFile.getTestResultsBunch().add(testResultsBunch);
		}
		try {
			JAXBUtils.objectToXmlFile(targetExportPath + "/testresults.xml",
					indexFile);
			InOutUtils.zip(new File(targetExportPath), new File(
					targetRootExportPath + "/" + exportId + ".zip"));
		} catch (IOException e) {
			logger.error("Error while writing export index file");
			logger.debug("IOException error stack: ", e);
		} catch (JAXBException e) {
			logger.error("Error while writing export index file");
			logger.debug("JAXBException error stack: ", e);
		}
	}

}
