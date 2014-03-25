/**
 * 
 */
package org.w3c.wai.accessdb.rest;

import java.util.List;

import javax.ws.rs.core.MediaType;

import junit.framework.Assert;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.wai.accessdb.AbstractRestServiceTest;
import org.w3c.wai.accessdb.jaxb.ElementWrapper;
import org.w3c.wai.accessdb.om.Technique;
import org.w3c.wai.accessdb.rest.resources.WCAG2Resource;
import org.w3c.wai.accessdb.services.DBInitService;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;

/**
 * @author evangelos.vlachogiannis
 * @since 08.05.2012
 */
public class TestRequirementsRestService extends
		AbstractRestServiceTest<WCAG2Resource> {
	/**
	 * Default Logger.
	 */
	protected static Logger LOG = LoggerFactory
			.getLogger(AbstractRestServiceTest.class);

	@Test
	public void testGetTechnique() {
		ClientResponse<Technique> response = null;
		Technique requirement = null;
		try {
			ClientRequest request = new ClientRequest(SERVER_URL
					+ "/requirements/technique/{type}/byid/{id}");
			request.accept(MediaType.APPLICATION_JSON).pathParameter("type",
					"HTML");
			request.accept(MediaType.APPLICATION_JSON)
					.pathParameter("id", "H2");
			response = request.get(Technique.class);
			if (response.getStatus() != 200)
				throw new RuntimeException("Failed!");
			requirement = response.getEntity();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.warn(e.getLocalizedMessage());
		} finally {
			response.releaseConnection();
		}
		System.out.println(requirement.getNameId());
		Assert.assertEquals(requirement.getNameId(), "H2");

	}

	@Test
	public void testGetTechniques() {
		ClientResponse<ElementWrapper<Technique>> response = null;
		ElementWrapper<Technique> requirements = null;
		try {
			ClientRequest request = new ClientRequest(SERVER_URL
					+ "/requirements/techniques/{type}");
			request.accept(MediaType.APPLICATION_JSON).pathParameter("type",
					"HTML");
			response = request
					.get(new GenericType<ElementWrapper<Technique>>() {
					});
			if (response.getStatus() != 200)
				throw new RuntimeException("Failed!");
			requirements = response
					.getEntity(new GenericType<ElementWrapper<Technique>>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
			LOG.warn(e.getLocalizedMessage());
		} finally {
			response.releaseConnection();
		}
		System.out.println(requirements.getList().size());
		Assert.assertEquals(requirements.getList().size(), 60);

	}

	@Test
	public void testGetTechniquesByTerm() {
		String term = "H2";
		ClientResponse<List<Technique>> response = null;
		List<Technique> requirements = null;
		try {
			ClientRequest request = new ClientRequest(SERVER_URL
					+ "/requirements/techniques/{type}/byterm/{term}");
			request.accept(MediaType.APPLICATION_JSON).pathParameter("type",
					"HTML");
			request.accept(MediaType.APPLICATION_JSON).pathParameter("term",
					term);
			response = request.get(new GenericType<List<Technique>>() {
			});
			if (response.getStatus() != 200)
				throw new RuntimeException("Failed!");
			requirements = response
					.getEntity(new GenericType<List<Technique>>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
			LOG.warn(e.getLocalizedMessage());
		} finally {
			response.releaseConnection();
		}
		System.out.println(requirements.size());
		Assert.assertEquals(requirements.size(), 5);

	}

	//@Test
	public void testEcho() {
		ClientResponse<String> response = null;
		String msg = null;
		try {
			ClientRequest request = new ClientRequest(SERVER_URL
					+ "/requirements/echo/{msg}");
			request.accept(MediaType.APPLICATION_JSON).pathParameter("msg",
					"test1");
			response = request.get(String.class);
			if (response.getStatus() != 200)
				throw new RuntimeException("Failed!");
			msg = response.getEntity();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.releaseConnection();
		}
		System.out.println(msg);
		Assert.assertEquals(msg, "test1");

	}

	@Override
	protected Class<?> getResourceClass() {
		return WCAG2Resource.class;
	}

	@Override
	protected void populate() throws ASBPersistenceException {
		DBInitService.INSTANCE.initRequirements();

	}

}
