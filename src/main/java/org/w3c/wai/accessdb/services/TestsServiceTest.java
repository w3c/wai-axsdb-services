package org.w3c.wai.accessdb.services;

import javax.xml.bind.JAXBException;

import org.junit.Test;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;

public class TestsServiceTest {

	@Test
	public void testImportTests() throws ASBPersistenceException, JAXBException, ClassNotFoundException {
		String path = "C:\\code\\wai-axsdb-services\\src\\main\\java\\importdata\\index.xml";
		
		TestsService.INSTANCE.importTests(path);
	}

}
