/**
 * 
 */
package org.w3c.wai.accessdb.eao;

import org.junit.Ignore;
import org.junit.Test;
import org.w3c.wai.accessdb.om.testunit.RefFileType;
import org.w3c.wai.accessdb.om.testunit.Step;
import org.w3c.wai.accessdb.om.testunit.Subject;
import org.w3c.wai.accessdb.om.testunit.TestProcedure;
import org.w3c.wai.accessdb.services.TechniquesService;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;

/**
 * @author evangelos.vlachogiannis
 * @since 14.03.12
 */
@Ignore
public class TestEntitiesEAO extends AbstractTest{
	
	//@Test
	public void testInsertProcedure() throws ASBPersistenceException
	{
		TestProcedure p = new TestProcedure();
		p.getStep().add(new Step("dfdf"));
		p.getStep().add(new Step("dfdf"));
		p.setYesNoQuestion("dsfdsfds");
		p.setExpectedResult(true);
	//	EAOManager.INSTANCE.getObjectEAO().persist(p);
	//	assertEquals(EAOManager.INSTANCE.getObjectEAO().findAll().size(),1);
		
	}
	

	//@Test
	public void testInsertSpecification() {
		assertEquals(EAOManager.INSTANCE.getWebTechnologyEAO().findAll().size(), 3);
	}
	
	//@Test
	public void testInsertUAgent() {
		assertEquals(EAOManager.INSTANCE.getProductEAO().findAll().size(), 2);
	}
	//@Test
	public void testInsertProduct() {
		assertEquals(EAOManager.INSTANCE.getProductEAO().findAll().size(), 2);
	}
	
	//@Test
	public void testInsertSubject() throws ASBPersistenceException
	{
		RefFileType f = new RefFileType();
		f.setSrc("srccc");
		f.setMediatype("text");
		Subject s = new Subject();
		s.setTestFile(f);
		EAOManager.INSTANCE.getObjectEAO().persist(f);
		assertEquals(EAOManager.INSTANCE.getRefFileTypeEAO().findAll().size(),1);
		
	}
	
	//@Test
	public void testGetTechniquesByTerm() {
		assertEquals(TechniquesService.INSTANCE.getTechniquesByTerm("H4").size(), 9);
	}
	
	@Test
	public void testdeltest() {
		
	}
}
