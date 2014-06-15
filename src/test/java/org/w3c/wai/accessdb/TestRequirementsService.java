/**
 * 
 */
package org.w3c.wai.accessdb;

import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import junit.framework.Assert;

import org.junit.Test;
import org.w3c.wai.accessdb.om.Technique;
import org.w3c.wai.accessdb.services.TechniquesService;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;

/**
 * @author evangelos.vlachogiannis
 * @since 08.05.2012
 */
public class TestRequirementsService 
{
    @Test
    public void testRetrieveTechniques() throws ASBPersistenceException
    {
  //  	ByteArrayOutputStream baos = new ByteArrayOutputStream();
		JAXBContext jc;
		List<Technique> reqs = null;
		try {
			jc = JAXBContext.newInstance(Technique.class);
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			reqs = TechniquesService.INSTANCE.retrieveTechniques("ARIA");
			//EAOManager.INSTANCE.getReqEAO().persist(reqs);
			System.out.println(reqs.size());
			m.marshal(reqs.get(0), System.out);
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Assert.assertEquals(reqs.size() ,60);
    }
   

}
