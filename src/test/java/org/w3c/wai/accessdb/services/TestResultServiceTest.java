package org.w3c.wai.accessdb.services;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.om.Technique;
import org.w3c.wai.accessdb.om.TestResult;
import org.w3c.wai.accessdb.om.TestResultsBunch;
import org.w3c.wai.accessdb.om.TestingProfile;
import org.w3c.wai.accessdb.om.product.AssistiveTechnology;
import org.w3c.wai.accessdb.om.product.Platform;
import org.w3c.wai.accessdb.om.product.UAgent;
import org.w3c.wai.accessdb.om.testunit.TestProcedure;
import org.w3c.wai.accessdb.om.testunit.TestUnitDescription;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;

public class TestResultServiceTest {
	private static final Logger logger =  LoggerFactory.getLogger(TestResultServiceTest.class);
	
	@Test
	public void testSaveResult() throws ASBPersistenceException{
		DBInitService.INSTANCE.initAll();
		for (int k = 0; k < 15; k++) 
		{
			for (int i = 0; i < 5; i++) 
			{
				this.populateTestWithResult();	
			}
			long no = EAOManager.INSTANCE.getTestResultEAO().findAll().size();
			Assert.assertEquals(no, 5*(k+1));	
		}
		
	}
	
	//@Test
	public void testdeleteDeepTestUnitById() throws ASBPersistenceException{
		DBInitService.INSTANCE.initAll();
		TestUnitDescription tu = populateTestWithResult();
		logger.info(tu.getTestUnitId());
		long no = EAOManager.INSTANCE.getTestResultEAO().findByTestUnitId(tu.getTestUnitId()).size();
		logger.info(String.valueOf(no));
		try {
			TestResultsService.INSTANCE.deleteTestResultsByTestNameId(tu.getTestUnitId());
		} catch (ASBPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long no_new = EAOManager.INSTANCE.getTestResultEAO().findByTestUnitId(tu.getTestUnitId()).size();
		logger.info(String.valueOf(no_new));
		Assert.assertEquals(no>0, true);
		Assert.assertEquals(no_new, 0);
		
	}
	
	public static TestUnitDescription populateTestWithResult() throws ASBPersistenceException{
		TestUnitDescription tu = new TestUnitDescription();
		Technique t = EAOManager.INSTANCE.getTechniqueEAO().findAll().get(0);
		tu.setTechnique(t);
		tu.setTestProcedure(new TestProcedure());
		tu = TestsService.INSTANCE.insertOrUpdateTestUnit(tu);
		EAOManager.INSTANCE.getTestUnitDescriptionEAO().persist(tu);
		TestResultsBunch testResultsBunch = new TestResultsBunch();
		TestingProfile p = new TestingProfile();
		p.setAssistiveTechnology(new AssistiveTechnology("JAWS", "11"));
		p.setPlatform(new Platform("Win", "11", ""));
		p.setUserAgent(new UAgent("Chrome","11"));
		//p = EAOManager.INSTANCE.getTestingProfileEAO().persist(p);
		TestResult r1 = new TestResult();
		r1.setComment("com1");
		r1.setResultValue(true);
		r1.setRunDate(new Date());
		r1.setTestingProfile(p);
		r1.setTestUnitDescription(tu);
		testResultsBunch.getResults().add(r1);
		testResultsBunch.setDate(new Date());
		testResultsBunch.setUser(EAOManager.INSTANCE.getUserEAO().findByUserId("anon"));
		testResultsBunch.setDate(new Date());

		TestResultsService.INSTANCE.saveResultsBunch(testResultsBunch);
		logger.info("saved bunch id: " + testResultsBunch.getId());
		return tu;
	}
}
