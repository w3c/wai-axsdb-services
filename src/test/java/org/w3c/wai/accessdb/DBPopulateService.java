package org.w3c.wai.accessdb;

import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.eao.TestUnitDescriptionEAO;
import org.w3c.wai.accessdb.helpers.TestUnitFactory;
import org.w3c.wai.accessdb.om.Technique;
import org.w3c.wai.accessdb.om.User;
import org.w3c.wai.accessdb.om.UserTestingProfile;
import org.w3c.wai.accessdb.om.product.AssistiveTechnology;
import org.w3c.wai.accessdb.om.product.Platform;
import org.w3c.wai.accessdb.om.product.UAgent;
import org.w3c.wai.accessdb.om.testunit.RefFileType;
import org.w3c.wai.accessdb.om.testunit.Step;
import org.w3c.wai.accessdb.om.testunit.Subject;
import org.w3c.wai.accessdb.om.testunit.TestProcedure;
import org.w3c.wai.accessdb.om.testunit.TestUnitDescription;
import org.w3c.wai.accessdb.services.TechniquesService;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;
public enum DBPopulateService {
	INSTANCE;
	
	public void populateTestUnitDesc() throws ASBPersistenceException
	{
		//WebTechnology webTechnology = EAOManager.INSTANCE.getWebTechnologyEAO().findByNameId("HTML");
		TestUnitDescriptionEAO eao = new TestUnitDescriptionEAO();
		eao.removeAll();
		TestUnitFactory f = new TestUnitFactory();
		Technique f2 = TechniquesService.INSTANCE.getTechniquesByTerm("H2").get(0);
		
		TestUnitDescription testUnitDescription_0 = new TestUnitDescription();
		testUnitDescription_0.setCreator("Test Author");
		testUnitDescription_0.setTestUnitId("F1_000");
		testUnitDescription_0.setTitle("A title");
		testUnitDescription_0.setLanguage("en");
		testUnitDescription_0.setDate(new Date());
		testUnitDescription_0.setTechnique(f2);
		TestProcedure p1 = f.createTestUnitDescriptionProcedure();
		p1.setYesNoQuestion("the question");
		p1.setExpectedResult(true);
		p1.getStep().add(new Step("step 1"));
		p1.getStep().add(new Step("step 2"));
		testUnitDescription_0.setTestProcedure(p1);
		Subject s0 = f.createTestUnitDescriptionSubject();
		RefFileType file1 = new RefFileType();
		file1.setMediatype(MediaType.TEXT_HTML);
		file1.setSrc(testUnitDescription_0.getTestUnitId() + ".html");
		s0.setTestFile(file1);
		testUnitDescription_0 = eao.persist(testUnitDescription_0);

		
		TestUnitDescription testUnitDescription_1 = new TestUnitDescription();
		testUnitDescription_1.setCreator("Test Author");
		testUnitDescription_1.setTestUnitId("F1_001");
		testUnitDescription_1.setDate(new Date());
		testUnitDescription_1.setTechnique(f2);
		TestProcedure p2 = f.createTestUnitDescriptionProcedure();
		p2.setYesNoQuestion("the question");
		p2.setExpectedResult(false);
		testUnitDescription_1.setTestProcedure(p2);
		testUnitDescription_1.getTestProcedure().getStep().add(new Step("step 1"));
		testUnitDescription_1.getTestProcedure().getStep().add(new Step("step 2"));
		Subject s1 = f.createTestUnitDescriptionSubject();
		RefFileType file2 = new RefFileType();
		file2.setMediatype(MediaType.TEXT_HTML);
		file2.setSrc(testUnitDescription_1.getTestUnitId() + ".html");
		s1.setTestFile(file2);
		testUnitDescription_1.setSubject(s1);
		eao.persist(testUnitDescription_1);
		//EAOManager.INSTANCE.getWebTechnologyEAO().persist(webTechnology);

	}
	
	
	public void populateUsers() throws ASBPersistenceException
	{
		//TODO
		/*User user_c = new User();
		user_c.setUserId("expert");
		user_c.setPass("expert");
		user_c.getUserRoles().add(e).setRole("expert");
		user_c.setMail("expert");
		EAOManager.INSTANCE.getUserEAO().persist(user_c);
		
		User user = new User();
		user.setUserId("user");
		user.setPass("user");
		user.setRole("user");
		user.setMail("user");
		EAOManager.INSTANCE.getUserEAO().persist(user);*/
		
		
	}
	public void populateTestingProfiles(User user) throws ASBPersistenceException
	{
		
		UserTestingProfile p1 = new UserTestingProfile();
		p1.setProfileName("profile1");
		p1.getProfile().setAssistiveTechnology(new AssistiveTechnology("Window Eyes","6"));
		p1.getProfile().setUserAgent(new UAgent("Chrome","8"));
		p1.getProfile().setPlatform(new Platform("Linux","15" ,"i686"));  
		p1 = EAOManager.INSTANCE.getUserTestingProfileEAO().persist(p1);
		user.getUserTestingProfiles().add(p1);
		
		UserTestingProfile p2 = new UserTestingProfile();
		p2.setProfileName("profile2");
		p2.getProfile().setAssistiveTechnology(new AssistiveTechnology("JAWS","10")); 
		p2.getProfile().setUserAgent(new UAgent("Mozilla","15"));
		p2.getProfile().setPlatform(new Platform("Windows", "7","i686"));
		p2 = EAOManager.INSTANCE.getUserTestingProfileEAO().persist(p2);
		user.getUserTestingProfiles().add(p2);
		EAOManager.INSTANCE.getUserEAO().persist(user);
	}

	public static void main(String[] args) throws ASBPersistenceException
	{
		DBPopulateService.INSTANCE.populateTestUnitDesc();
	}
	
}
