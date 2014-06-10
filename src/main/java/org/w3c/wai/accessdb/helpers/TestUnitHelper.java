package org.w3c.wai.accessdb.helpers;

import java.io.File;

import org.w3c.wai.accessdb.om.Technique;
import org.w3c.wai.accessdb.om.testunit.RefFileType;
import org.w3c.wai.accessdb.om.testunit.Step;
import org.w3c.wai.accessdb.om.testunit.TestProcedure;
import org.w3c.wai.accessdb.om.testunit.TestUnitDescription;
import org.w3c.wai.accessdb.services.ConfigService;
import org.w3c.wai.accessdb.utils.InOutUtils;

public class TestUnitHelper {

	public static TestUnitDescription generateTestUnitDescriptionId(
			TestUnitDescription testUnitDescription) {
		String keySize=ConfigService.INSTANCE
				.getConfigParam(ConfigService.KEY_GENERATOR_SIZE);
		String id = testUnitDescription.getTechnique().getNameId() + "_"
				+ String.format("%0"+ keySize + "d", testUnitDescription.getId());
		testUnitDescription.setTestUnitId(id);
		return testUnitDescription;
	}

	public static File getTestFile(TestUnitDescription testUnitDescription) {
		String src = testUnitDescription.getSubject().getTestFile().getSrc();
		return new File(src);
	}

	public static String getTestUnitFolderPath(
			TestUnitDescription testUnitDescription) {
		String webTechnologyPath = testUnitDescription.getTechnique().getWebTechnology().getNameId();
		String folderPref = ConfigService.INSTANCE
				.getConfigParam(ConfigService.FORM_TESTUNIT_FOLDER);
		String folderName = testUnitDescription.getTestUnitId();
		String path = folderPref + webTechnologyPath + "/" + folderName + "/";
		return path;
	}
	

	public static TestUnitDescription cloneTest(TestUnitDescription tu){
		TestUnitDescription test = (TestUnitDescription) InOutUtils.deepClone(tu);
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

}
