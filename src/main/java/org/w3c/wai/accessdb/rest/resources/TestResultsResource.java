package org.w3c.wai.accessdb.rest.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.jaxb.ElementWrapper;
import org.w3c.wai.accessdb.jaxb.SimpleTestResult;
import org.w3c.wai.accessdb.jaxb.TestResultDataOverview;
import org.w3c.wai.accessdb.jaxb.TestResultFilter;
import org.w3c.wai.accessdb.jaxb.TestResultTestOveview;
import org.w3c.wai.accessdb.jaxb.TestResultViewData;
import org.w3c.wai.accessdb.jaxb.TestResultViewTable;
import org.w3c.wai.accessdb.om.TestResult;
import org.w3c.wai.accessdb.om.TestResultsBunch;
import org.w3c.wai.accessdb.services.ATService;
import org.w3c.wai.accessdb.services.TestResultsService;
import org.w3c.wai.accessdb.services.TestingSessionService;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;
import org.w3c.wai.accessdb.utils.AuthenticationException;

/**
 * @author evangelos.vlachogiannis
 * @since 30.05.12
 */
@Path("testresult")
public class TestResultsResource {
	private static final Logger logger = LoggerFactory
			.getLogger(TestResultsResource.class);

	/**
	 * Save a bunch of test results
	 * 
	 * @param testResultsBunch
	 * @return
	 */
	@POST
	@Path("commit/bunch")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response saveBunch(TestResultsBunch testResultsBunch) {
		try {
			if (testResultsBunch.getResults().size() > 0) {
				TestResultsBunch t = TestResultsService.INSTANCE
						.saveResultsBunch(testResultsBunch);
				return Response.ok(t.getId()).build();
			} else
				return Response.noContent().build();
		} catch (ASBPersistenceException e) {
			return Response.notModified(e.getLocalizedMessage()).build();
		}
	}

	/**
	 * Get unique Assistive Technologies as TreeDataNode that appear in test
	 * results in DB based on the given filter
	 * 
	 * @param filter
	 * @return
	 */
	@Path("browse/at/tree")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response getATTree(TestResultFilter filter) {
		return Response.ok(ATService.INSTANCE.getATNode(filter)).build();
	}

	/**
	 * Get unique User Agents (Browsers) as TreeDataNode that appear in test
	 * results in DB based on the given filter
	 * 
	 * @param filter
	 * @return
	 */
	@Path("browse/ua/tree")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response getUATree(TestResultFilter filter) {
		return Response.ok(ATService.INSTANCE.getUANode(filter)).build();
	}

	/**
	 * Get unique Platforms (Operating Systems) as TreeDataNode that appear in
	 * test results in DB based on the given filter
	 * 
	 * @param filter
	 * @return
	 */
	@Path("browse/os/tree")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response getOSTree(TestResultFilter filter) {
		return Response.ok(ATService.INSTANCE.getOSNode(filter)).build();
	}

	/**
	 * Get unique Platforms (Operating Systems) as TreeDataNode that appear in
	 * test results in DB based on the given filter
	 * 
	 * @return
	 */
	@Path("browse/technologies/tree")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getTechnologiesTree() {
		return Response.ok(ATService.INSTANCE.getTechnologiesTree()).build();
	}

	/**
	 * Get TestResult by DB Id
	 * 
	 * @param id
	 * @return
	 */
	@Path("{id}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findById(@PathParam("id") String id) {
		TestResult r = null;
		try {
			r = EAOManager.INSTANCE.getTestResultEAO().findById(
					Long.parseLong(id));
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(id).build();
		}
		if (r == null)
			return Response.status(Status.NOT_FOUND).entity(id).build();
		return Response.status(Status.OK).entity(r).build();
	}

	/**
	 * Find test results based on filter
	 * 
	 * @param filter
	 * @return
	 */
	@Path("filter")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findByFilter(TestResultFilter filter) {
		List<SimpleTestResult> results;
		try {
			results = TestResultsService.INSTANCE.getResults(filter);
			return Response.status(Status.OK).entity(results).build();
		} catch (ASBPersistenceException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getStackTrace()).build();
		}
	}

	/**
	 * Find test results based on filter
	 * 
	 * @param filter
	 * @return
	 */
	@Path("filter/test/{testUnitId}")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findByTestAndFilter(TestResultFilter filter,
			@PathParam("testUnitId") String testUnitId) {
		List<SimpleTestResult> results;
		try {
			results = TestResultsService.INSTANCE.getResults(filter);
			return Response.status(Status.OK).entity(results).build();
		} catch (ASBPersistenceException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getStackTrace()).build();
		}
	}

	/**
	 * Top level results view: by technique overall pass and fail
	 * 
	 * @param filter
	 * @return
	 */
	@Path("browse/bytechnique/overview")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response findByFilterTestResultTechniqueOveview(
			TestResultFilter filter) {
		List<TestResultDataOverview> res = TestResultsService.INSTANCE
				.loadTestResultDataOverview(filter);
		return Response.ok(new ElementWrapper<TestResultDataOverview>(res))
				.build();
	}

	/**
	 * Second level results view: by testunit overall pass and fail
	 * 
	 * @param filter
	 * @return
	 */
	@Path("browse/bytest/overview/{techNameId}")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response findByFilterTestResultTestOveview(TestResultFilter filter,
			@PathParam("techNameId") String techNameId) {
		List<TestResultTestOveview> res = TestResultsService.INSTANCE
				.findByFilterTestResultTestOveview(filter, techNameId);
		return Response.ok(new ElementWrapper<TestResultTestOveview>(res))
				.build();
	}

	@Path("browse/resultsview/{techid}")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response loadTestResultViewData(TestResultFilter filter,
			@PathParam("techid") String techid) {
		List<TestResultViewData> res = TestResultsService.INSTANCE
				.loadTestResultViewData(filter, techid);
		return Response.ok(new ElementWrapper<TestResultViewData>(res)).build();
	}

	@Path("browse/fullviewtechnique/{techid}")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response loadTestResultFullViewTechnique(TestResultFilter filter,
			@PathParam("techid") String techid) {
		try {
			TestResultViewTable res = TestResultsService.INSTANCE
					.loadTestResultFullViewTechnique(filter, techid);
			return Response.ok(res).build();
		} catch (Exception e) {
			return Response.serverError().entity(e.getStackTrace()).build();
		}
	}

	@Path("browse/fullviewtest/{testUnitId}")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response loadTestResultFullViewTest(TestResultFilter filter,
			@PathParam("testUnitId") String testUnitId) {
		try {
			TestResultViewTable res = TestResultsService.INSTANCE
					.loadTestResultViewTest(filter, testUnitId);
			return Response.ok(res).build();
		} catch (Exception e) {
			return Response.serverError().entity(e.getStackTrace()).build();
		}
	}

	@Path("{sessionId}/{id}")
	@DELETE
	public Response deleteTestResultById(
			@PathParam("sessionId") String sessionId, @PathParam("id") String id) {
		try {
			if (!TestingSessionService.INSTANCE
					.isAuthenticatedAsAdmin(sessionId))
				return Response.status(Response.Status.UNAUTHORIZED).build();
		} catch (AuthenticationException e) {
			return Response.serverError().build();
		}
		try {
			TestResultsService.INSTANCE
					.deleteTestResultById(Long.parseLong(id));
			return Response.status(Response.Status.OK).build();
		} catch (NumberFormatException e) {
			logger.error(e.getLocalizedMessage());
			return Response.status(Response.Status.BAD_REQUEST).build();
		} catch (ASBPersistenceException e) {
			logger.error(e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		}
	}
	@Path("put/{sessionId}")
	@POST
	public Response updateTestResult(
			@PathParam("sessionId") String sessionId, TestResult r) {
		try {
			if (!TestingSessionService.INSTANCE
					.isAuthenticatedAsAdmin(sessionId))
				return Response.status(Response.Status.UNAUTHORIZED).build();
		} catch (AuthenticationException e) {
			return Response.serverError().build();
		}
		try {
			TestResultsService.INSTANCE
					.updateTestResult(r);
			return Response.status(Response.Status.OK).build();
		} catch (NumberFormatException e) {
			logger.error(e.getLocalizedMessage());
			return Response.status(Response.Status.BAD_REQUEST).build();
		} catch (ASBPersistenceException e) {
			logger.error(e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		}
	}
}
