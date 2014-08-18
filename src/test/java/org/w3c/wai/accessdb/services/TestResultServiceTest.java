package org.w3c.wai.accessdb.services;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.wai.accessdb.DummyDataFactory;
import org.w3c.wai.accessdb.eao.AbstractTest;
import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.jaxb.TestResultFilter;
import org.w3c.wai.accessdb.jaxb.TestResultViewTable;
import org.w3c.wai.accessdb.jaxb.TestResultViewTableCell;
import org.w3c.wai.accessdb.om.TestResult;
import org.w3c.wai.accessdb.om.TestResultsBunch;
import org.w3c.wai.accessdb.om.testunit.TestUnitDescription;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;

public class TestResultServiceTest extends AbstractTest {
	private static final Logger logger = LoggerFactory
			.getLogger(TestResultServiceTest.class);
	
	@Test
	public void testResultsFilterWithStatus() throws ClassNotFoundException, ASBPersistenceException, IOException{
		TestUnitDescription tu = DummyDataFactory.populateOneTestWithResult();
		logger.info(tu.getStatus().toString());
		TestResultFilter filter = new TestResultFilter();
		String all = EAOManager.INSTANCE.getTestResultEAO().countAllByTechniqueNameId(tu.getTechnique().getNameId());
		filter.getStatusList().add("UNCONFIRMED");
		filter.getStatusList().add("ACCEPTED");
		TestResultViewTable res = TestResultsService.INSTANCE.loadTestResultFullViewTechnique(filter, tu.getTechnique().getNameId());
		System.out.print(res.getRows().size());
	}

	// @Test
	public void testLoadTestResultViewTest() throws ASBPersistenceException {
		TestResultFilter filter = new TestResultFilter();
		String testUnitId = DummyDataFactory.getRandomTest().getTestUnitId();
		TestResultViewTable res = TestResultsService.INSTANCE
				.loadTestResultViewTest(filter, testUnitId);
		boolean firstRow = true;
		boolean firstCol = true;
		int all = 0;
		for (List<TestResultViewTableCell> row : res.getRows()) {
			if (firstRow) {
				firstRow = false;
				continue;
			}
			firstCol = true;
			for (TestResultViewTableCell cell : row) {
				if (firstCol) {
					firstCol = false;
					continue;
				}
				all = all + Integer.parseInt(cell.getNoOfAll());
			}
		}
		int allSimple = EAOManager.INSTANCE.getTestResultEAO()
				.findByTestUnitId(testUnitId).size();
		filter.getTests().add(testUnitId);
		int allFilter = TestResultsService.INSTANCE.getResults(filter).size();
		Assert.assertEquals(allFilter, all);
		Assert.assertEquals(res.getRows().size() >= 1, true);
	}

	//@Test
	public void testLoadTestResultFullViewTechnique()
			throws ASBPersistenceException {
		TestResultFilter filter = new TestResultFilter();
		String id = DummyDataFactory.getRandomTechnique().getNameId();
		TestResultViewTable res = TestResultsService.INSTANCE
				.loadTestResultFullViewTechnique(filter, id);
		boolean firstRow = true;
		boolean firstCol = true;
		int all = 0;
		for (List<TestResultViewTableCell> row : res.getRows()) {
			if (firstRow) {
				firstRow = false;
				System.out.println("----------------------");
				continue;
			}
			firstCol = true;
			for (TestResultViewTableCell cell : row) {
				System.out.println(" | ");
				if (firstCol) {
					System.out.println("|");
					firstCol = false;
					continue;
				}
				System.out
						.println(cell.getNoOfPass() + "/" + cell.getNoOfAll());
				all = all + Integer.parseInt(cell.getNoOfAll());
			}
		}
		int allSimple = EAOManager.INSTANCE.getTestResultEAO()
				.findByTechniqueNameId(id).size();
		filter.getTechniques().add(id);
		int allFilter = TestResultsService.INSTANCE.getResults(filter).size();
		Assert.assertEquals(allFilter, all);
		Assert.assertEquals(allSimple, all);
	}

	// @Test
	public void testSaveResult() throws ASBPersistenceException,
			ClassNotFoundException, IOException {
		TestUnitDescription tu = DummyDataFactory.populateOneTestWithResult();
		Assert.assertEquals(tu.getId() > 0, true);
	}

	// @Test
	public void testdeleteDeepTestUnitById() throws ASBPersistenceException,
			ClassNotFoundException, IOException {
		DBInitService.INSTANCE.initAll();
		TestUnitDescription tu = DummyDataFactory.populateOneTestWithResult();
		logger.info(tu.getTestUnitId());
		long no = EAOManager.INSTANCE.getTestResultEAO()
				.findByTestUnitId(tu.getTestUnitId()).size();
		logger.info(String.valueOf(no));
		try {
			TestResultsService.INSTANCE.deleteTestResultsByTestNameId(tu
					.getTestUnitId());
		} catch (ASBPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long no_new = EAOManager.INSTANCE.getTestResultEAO()
				.findByTestUnitId(tu.getTestUnitId()).size();
		logger.info(String.valueOf(no_new));
		Assert.assertEquals(no > 0, true);
		Assert.assertEquals(no_new, 0);
	}

	//@Test
	public void testdeleteTestResultById() throws ASBPersistenceException,
			ClassNotFoundException, IOException {
		DBInitService.INSTANCE.initAll();
		TestUnitDescription tu = DummyDataFactory.populateOneTestWithResult();
		TestResultsBunch bunch = DummyDataFactory.createDummyResults(tu, DummyDataFactory.createDummyProfile(), 1);
		long id =bunch.getResults().get(0).getId();
		logger.info(tu.getTestUnitId());
		try {
			TestResultsService.INSTANCE.deleteTestResultById(id);
			TestResult res = EAOManager.INSTANCE.getTestResultEAO().findById(id);
			Assert.assertNull(res);
		} catch (ASBPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
