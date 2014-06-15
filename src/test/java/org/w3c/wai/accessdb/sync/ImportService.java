package org.w3c.wai.accessdb.sync;

import java.io.IOException;
import java.util.Date;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.w3c.wai.accessdb.helpers.TestUnitFactory;
import org.w3c.wai.accessdb.om.Technique;
import org.w3c.wai.accessdb.om.testunit.RefFileType;
import org.w3c.wai.accessdb.om.testunit.Step;
import org.w3c.wai.accessdb.om.testunit.Subject;
import org.w3c.wai.accessdb.om.testunit.TestProcedure;
import org.w3c.wai.accessdb.om.testunit.TestUnitDescription;
import org.w3c.wai.accessdb.utils.JAXBUtils;

public class ImportService {
 public static void main(String[] args) throws IOException, JAXBException {
	 TestUnitFactory f = new TestUnitFactory();
		Technique f2 = new Technique();
		f2.setNameId("F2");
		
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

		JAXBUtils.objectToXmlFile("C:\\Users\\Vlachogiannis\\Downloads\\a.xml",testUnitDescription_1);
}
}
