package org.w3c.wai.accessdb.rest.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.jaxb.ElementWrapper;
import org.w3c.wai.accessdb.jaxb.TestResultDataOverview;
import org.w3c.wai.accessdb.jaxb.TestResultFilter;
import org.w3c.wai.accessdb.jaxb.TestResultFullViewTechnique;
import org.w3c.wai.accessdb.jaxb.TestResultViewData;
import org.w3c.wai.accessdb.om.TestResult;
import org.w3c.wai.accessdb.om.TestResultsBunch;
import org.w3c.wai.accessdb.services.ATService;
import org.w3c.wai.accessdb.services.TestResultsService;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;

/**
 * @author evangelos.vlachogiannis
 * @since 30.05.12
 */
@Path("testresult")
public class TestResultsResource {
	
	@POST
	@Path("commit/bunch")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response save(TestResultsBunch testResultsBunch) {
		try{
			if(testResultsBunch.getResults().size()>0)
			{
				TestResultsBunch t = TestResultsService.INSTANCE.saveResultsBunch(testResultsBunch);
				return Response.ok(t.getId()).build();				
			}
			else
				return Response.noContent().build();
		}
		catch(ASBPersistenceException e){
			return Response.notModified(e.getLocalizedMessage()).build();
		}		
	}
	
	@Path("browse/resultsview/{techid}")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response loadTestResultViewData(TestResultFilter filter,@PathParam("techid") String techid) {
	    List<TestResultViewData> res = TestResultsService.INSTANCE.loadTestResultViewData(filter, techid);         
		return Response.ok(new ElementWrapper<TestResultViewData>(res)).build();
	}
	
	@Path("browse/dataoverview")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response loadTestResultDataOverview(TestResultFilter filter) {
        List<TestResultDataOverview> res = TestResultsService.INSTANCE.loadTestResultDataOverview(filter);
        return Response.ok(new ElementWrapper<TestResultDataOverview>(res)).build();
    }
	
	@Path("browse/fullviewtechnique/{techid}")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response loadTestResultFullViewTechnique(TestResultFilter filter,@PathParam("techid") String techid) {
	    try{
	        TestResultFullViewTechnique res = TestResultsService.INSTANCE.loadTestResultFullViewTechnique(filter, techid);
	        return Response.ok(res).build();
	    }
	    catch(Exception e)
	    {
	        return Response.serverError().entity(e.getStackTrace()).build();
	    }
    }
	
	@Path("browse/at/tree")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response getATTree(TestResultFilter filter) {
        return Response.ok(ATService.INSTANCE.getATNode(filter)).build();
    }
	
	@Path("browse/ua/tree")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response getUATree(TestResultFilter filter) {
		return Response.ok(ATService.INSTANCE.getUANode(filter)).build();
	}
	
	@Path("browse/os/tree")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response getOSTree(TestResultFilter filter) {
		return Response.ok(ATService.INSTANCE.getOSNode(filter)).build();
	}
	
	@Path("browse/technologies/tree")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getTechnologiesTree() {
		return Response.ok(ATService.INSTANCE.getTechnologiesTree()).build();
	}
	
	@Path("browse")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public ElementWrapper<TestResult> findAll() {
		return new ElementWrapper<TestResult>(EAOManager.INSTANCE
				.getTestResultEAO().findAll());
	}
	@Path("browse/byquery/{q}")
	@GET	
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getTestResultsByQuery(@PathParam("q") String q) {
		List<TestResult> res = new ArrayList<TestResult>();
		try{
			res = EAOManager.INSTANCE.getTestResultEAO().doSimpleSelectOnlyQuery(q);	
		}
		catch(Exception e)
		{
			return Response.serverError().build();
		}
		return Response.ok(new ElementWrapper<TestResult>(res)).build();
	}

	@Path("browse/testsuite/{tsId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public ElementWrapper<TestResult> findTestResultsByTestSuiteId(
			@PathParam("id") String id) {
		return new ElementWrapper<TestResult>(EAOManager.INSTANCE
				.getTestResultEAO().findAll());
	}

	@Path("browse/{id}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public TestResult getTestResult(@PathParam("id") String id) {
		return EAOManager.INSTANCE.getTestResultEAO().findById(
				Long.parseLong(id));
	}

	/*@Path("browse/testunit/{id}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<TestResult> findTestResultsByTestUnitId(
			@PathParam("id") String id) {
		return EAOManager.INSTANCE.getTestResultEAO().findByTestUnitId(id);
	}

	@Path("browse/testprofile/{id}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<TestResult> findTestResultsByTestProfiled(
			@PathParam("id") String id) {
		return EAOManager.INSTANCE.getTestResultEAO().findByTestProfileId(id);
	}*/
}
