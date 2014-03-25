/**
 * 
 */
package org.w3c.wai.accessdb.rest;

import java.io.File;

import javax.ws.rs.core.MediaType;

import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInputImpl;
import org.jboss.resteasy.plugins.providers.multipart.MultipartInput;
import org.jboss.resteasy.plugins.providers.multipart.MultipartInputImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.wai.accessdb.AbstractRestServiceTest;
import org.w3c.wai.accessdb.om.testunit.TestUnitDescription;
import org.w3c.wai.accessdb.rest.resources.TestUnitsResource;
import org.w3c.wai.accessdb.services.ConfigService;
import org.w3c.wai.accessdb.utils.InOutUtils;

/**
 * @author evangelos.vlachogiannis
 * @since 08.05.2012
 */
public class TestUnitRestService extends
		AbstractRestServiceTest<TestUnitsResource> {
	/**
	 * Default Logger.
	 */
	protected static Logger LOG = LoggerFactory
			.getLogger(AbstractRestServiceTest.class);

	@Test
	public void test() {
		ClientResponse<String> response = null;

		String tud = null;
		try {
			//ObjectMapper mapper = new ObjectMapper();
			/*TestUnitDescription tud_req = mapper.readValue(
			TestUnitRestService.class
					.getResourceAsStream("testunit_create.json"),
			TestUnitDescription.class);*/
			ClientRequest request = new ClientRequest(SERVER_URL
					+ "/testunit");
			File testFile = new File(TestUnitRestService.class.getResource("NewFile.html").getPath());
			String json = InOutUtils.readFile(TestUnitRestService.class.getResource("testunit_create.json").getPath());
			request.accept("multipart/form-data");
			request.accept(MediaType.APPLICATION_JSON).formParameter(ConfigService.INSTANCE.getConfigParam(ConfigService.FORM_TESTUNIT_FORMFIELD_TESTUNITDESCRIPTION), json);
			request.accept(MediaType.APPLICATION_OCTET_STREAM).formParameter("testfile", testFile);
			
			response = request.post(String.class);
			int status = response.getStatus();
			System.out.println(status);
			if (status != 200)
				throw new RuntimeException("Failed!");
			tud = response.getEntity();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.warn(e.getLocalizedMessage());
		} finally {
			response.releaseConnection();
		}
		System.out.println(tud);
		// Assert.assertEquals(tud.getId(), 1);

	}

	@Override
	protected Class<?> getResourceClass() {
		return TestUnitsResource.class;
	}

	@Override
	protected void populate() {

	}

}
