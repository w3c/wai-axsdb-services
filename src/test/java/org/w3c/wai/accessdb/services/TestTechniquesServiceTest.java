package org.w3c.wai.accessdb.services;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.om.testunit.TestUnitDescription;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;
 
public class TestTechniquesServiceTest {
	private static final Logger logger =  LoggerFactory.getLogger(TestTechniquesServiceTest.class);
	@Test
	public void deleteTestsAndResultsByTechniqueNameId() throws ASBPersistenceException{
		
		DBInitService.INSTANCE.initAll();
		TestUnitDescription tu = TestResultServiceTest.populateTestWithResult();
		logger.info(tu.getTestUnitId());
		long no = EAOManager.INSTANCE.getTestResultEAO().findByTestUnitId(tu.getTestUnitId()).size();
		logger.info(String.valueOf(no));
		try {
			TechniquesService.INSTANCE.deleteTestsAndResultsByTechniqueNameId("ARIA1");
		} catch (ASBPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long no_new = EAOManager.INSTANCE.getTestResultEAO().findByTestUnitId(tu.getTestUnitId()).size();
		logger.info(String.valueOf(no_new));
		Assert.assertEquals(no>0, true);
		Assert.assertEquals(no_new, 0);
		
	}
	
}
