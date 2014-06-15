package org.w3c.wai.accessdb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.om.Technique;
import org.w3c.wai.accessdb.om.TestResult;
import org.w3c.wai.accessdb.om.TestResultsBunch;
import org.w3c.wai.accessdb.om.TestingProfile;
import org.w3c.wai.accessdb.om.product.AssistiveTechnology;
import org.w3c.wai.accessdb.om.product.Platform;
import org.w3c.wai.accessdb.om.product.Product;
import org.w3c.wai.accessdb.om.product.UAgent;
import org.w3c.wai.accessdb.om.testunit.TestProcedure;
import org.w3c.wai.accessdb.om.testunit.TestUnitDescription;
import org.w3c.wai.accessdb.services.TestResultsService;
import org.w3c.wai.accessdb.services.TestsService;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;
import org.w3c.wai.accessdb.utils.InOutUtils;

public class DummyDataFactory {

	public static List<TestResultsBunch> createDummyTestsAndResults(int techniques, int profiles,
			int tests, int results) throws ASBPersistenceException, ClassNotFoundException, IOException {
		List<TestResultsBunch> l = new ArrayList<TestResultsBunch>();
		for (int g = 0; g < techniques; g++) {
			Technique t = getRandomTechnique();
			for (int i = 0; i < profiles; i++) {
				TestingProfile p = createDummyProfile();
				for (int k = 0; k < tests; k++) {
					TestingProfile p1 = (TestingProfile) InOutUtils.deepClone(p);
					TestUnitDescription test = createDummyTestForTechnique(t);
					TestResultsBunch b = createDummyResults(test, p1, results);
					l.add(b);
				}
			}
		}
		return l;
	}

	public static TestUnitDescription getRandomTest() {
		List<TestUnitDescription> tests = EAOManager.INSTANCE.getTestUnitDescriptionEAO()
				.findAll();
		int max = tests.size() - 1;
		int min = 0;
		int random = min + (int) (Math.random() * ((max - min) + 1));
		return tests.get(random);
	}
	public static Technique getRandomTechnique() {
		List<Technique> technques = EAOManager.INSTANCE.getTechniqueEAO()
				.findAll();
		int max = technques.size() - 1;
		int min = 0;
		int random = min + (int) (Math.random() * ((max - min) + 1));
		return technques.get(random);
	}
	public static TestingProfile createDummyProfile() {
		TestingProfile p = new TestingProfile();
		
		p.setAssistiveTechnology(new AssistiveTechnology(randomProduct()));
		p.setPlatform(new Platform(randomProduct()));
		p.setUserAgent(new UAgent(randomProduct()));
		return p;
	}

	public static TestResultsBunch createDummyResults(TestUnitDescription test,
			TestingProfile p, int many) throws ASBPersistenceException {
		TestResultsBunch testResultsBunch = new TestResultsBunch();
		for (int i = 0; i < many; i++) {
			TestResult r1 = new TestResult();
			r1.setComment("dummy");
			r1.setResultValue(true);
			r1.setRunDate(new Date());
			r1.setTestingProfile(p);
			r1.setTestUnitDescription(test);
			testResultsBunch.getResults().add(r1);
		}
		testResultsBunch.setDate(new Date());
		testResultsBunch.setUser(EAOManager.INSTANCE.getUserEAO().findByUserId(
				"anon"));
		testResultsBunch.setDate(new Date());
		TestResultsService.INSTANCE.saveResultsBunch(testResultsBunch);
		return testResultsBunch;
	}

	public static List<TestUnitDescription> createDummyTestsForTechnique(
			String tech, int many) throws ASBPersistenceException {
		Technique t = EAOManager.INSTANCE.getTechniqueEAO().findByNameId(tech);
		List<TestUnitDescription> tests = new ArrayList<>();
		for (int i = 0; i < many; i++) {
			TestUnitDescription test = DummyDataFactory
					.createDummyTestForTechnique(t);
			if (test.getId() > 0)
				tests.add(test);
		}
		return tests;
	}

	public static TestUnitDescription createDummyTestForTechnique(Technique t)
			throws ASBPersistenceException {
		TestUnitDescription tu = new TestUnitDescription();
		tu.setTechnique(t);
		tu.setTestProcedure(new TestProcedure());
		tu = TestsService.INSTANCE.insertOrUpdateTestUnit(tu);
		return tu;
	}

	private static Product randomProduct() {
		Random rng = new Random();
		String name = generateRandomString(rng, "abcdefsfgf", 5);
		String ver = generateRandomString(rng, "1234567890", 3);
		return new Product(name, ver);

	}

	private static String generateRandomString(Random rng, String characters,
			int length) {

		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		}
		return new String(text);
	}

	public static TestUnitDescription populateOneTestWithResult() throws ClassNotFoundException, ASBPersistenceException, IOException {
		List<TestResultsBunch> l = DummyDataFactory.createDummyTestsAndResults(1, 1, 1, 1);
		TestUnitDescription tu = l.get(0).getResults().get(0).getTestUnitDescription();
		return tu;
	}
}
