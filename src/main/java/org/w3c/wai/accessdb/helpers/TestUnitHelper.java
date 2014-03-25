package org.w3c.wai.accessdb.helpers;

import java.io.File;

import org.w3c.wai.accessdb.om.testunit.TestUnitDescription;
import org.w3c.wai.accessdb.services.ConfigService;

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


}
