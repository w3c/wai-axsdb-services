package org.w3c.wai.accessdb.helpers;

import java.io.File;
import java.io.IOException;

import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.jaxb.SimpleTestResult;
import org.w3c.wai.accessdb.om.TestResult;
import org.w3c.wai.accessdb.om.testunit.RefFileType;
import org.w3c.wai.accessdb.om.testunit.Step;
import org.w3c.wai.accessdb.om.testunit.TestProcedure;
import org.w3c.wai.accessdb.om.testunit.TestUnitDescription;
import org.w3c.wai.accessdb.services.ConfigService;
import org.w3c.wai.accessdb.utils.InOutUtils;

public class TestUnitHelper {

	public static TestUnitDescription generateTestUnitDescriptionId(
			TestUnitDescription testUnitDescription) {
		String keySize = ConfigService.INSTANCE
				.getConfigParam(ConfigService.KEY_GENERATOR_SIZE);
		String id = testUnitDescription.getTechnique().getNameId()
				+ "_"
				+ String.format("%0" + keySize + "d",
						testUnitDescription.getId());
		testUnitDescription.setTestUnitId(id);
		return testUnitDescription;
	}

	public static File getTestFile(TestUnitDescription testUnitDescription) {
		String src = testUnitDescription.getSubject().getTestFile().getSrc();
		return new File(src);
	}

	public static String getTestUnitFolderPath(
			TestUnitDescription testUnitDescription) {
		String webTechnologyPath = testUnitDescription.getTechnique()
				.getWebTechnology().getNameId();
		String folderPref = ConfigService.INSTANCE
				.getConfigParam(ConfigService.FORM_TESTUNIT_FOLDER);
		String folderName = testUnitDescription.getTestUnitId();
		String path = folderPref + webTechnologyPath + "/" + folderName + "/";
		return path;
	}

	public static TestUnitDescription cloneTest(TestUnitDescription tu) throws ClassNotFoundException, IOException {
		TestUnitDescription test = (TestUnitDescription) InOutUtils
				.deepClone(tu);
		test.setId(-1);
		TestProcedure tp = test.getTestProcedure();
		for (Step step : tp.getStep()) {
			step.setId(-1);
		}
		test.getSubject().getTestFile().setId(-1);
		for (RefFileType f : test.getSubject().getResourceFiles()) {
			f.setId(-1);
		}
		return test;
	}

	public static TestResult cloneTestResult(TestResult r) {
		TestResult res = new TestResult();
		res.setComment(r.getComment());
		res.setResultValue(r.isResultValue());
		res.setRunDate(r.getRunDate());
		res.setTestingProfile(r.getTestingProfile());
		res.setId(-1);
		if (res.getTestingProfile().getAssistiveTechnology() != null)
			res.getTestingProfile().getAssistiveTechnology().setId(-1);
		if (res.getTestingProfile().getPlatform() != null)
			res.getTestingProfile().getPlatform().setId(-1);
		if (res.getTestingProfile().getPlugin() != null)
			res.getTestingProfile().getPlugin().setId(-1);
		if (res.getTestingProfile().getUserAgent() != null)
			res.getTestingProfile().getUserAgent().setId(-1);
		return res;
	}
	public static TestResult adaptSimple2TestResult(SimpleTestResult sr){
    	TestResult r = new TestResult();
		r.setTestingProfile(sr.getTestingProfile());
		if (r.getTestingProfile().getAssistiveTechnology() != null)
			r.getTestingProfile().getAssistiveTechnology().setId(-1);
		if (r.getTestingProfile().getPlatform() != null)
			r.getTestingProfile().getPlatform().setId(-1);
		if (r.getTestingProfile().getPlugin() != null)
			r.getTestingProfile().getPlugin().setId(-1);
		if (r.getTestingProfile().getUserAgent() != null)
			r.getTestingProfile().getUserAgent().setId(-1);
		r.setComment(sr.getComment());
		r.setResultValue(sr.isResultValue());
		r.setRunDate(sr.getRunDate());
		r.setTestUnitDescription(EAOManager.INSTANCE.getTestUnitDescriptionEAO().findByTestUnitId(sr.getTestUnitId()));
    	return r;
    }

}
