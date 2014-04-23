package org.w3c.wai.accessdb.rest.resources;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.wai.accessdb.om.Technique;
import org.w3c.wai.accessdb.services.TechniquesService;
import org.w3c.wai.accessdb.services.TestingSessionService;
import org.w3c.wai.accessdb.services.TestsService;
import org.w3c.wai.accessdb.sync.TechniquesSpecParser;
import org.w3c.wai.accessdb.utils.AuthenticationException;

/**
 * @author evangelos.vlachogiannis
 * @since 30.05.12
 */
@Path("admin")
public class AdminResource {
	final static Logger logger = LoggerFactory
            .getLogger(AdminResource.class);
	
	//TODO: remove on production 
/*	@Path("initdb")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response initDB() throws ASBPersistenceException {
        logger.info("starting init.");
		DBInitService.INSTANCE.initAll();
		return Response.ok().build();
	}*/

	@Path("export/{sessionId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response exportAllTests(@PathParam("sessionId") String sessionId) {
		try {
			if (!TestingSessionService.INSTANCE.isAuthenticatedAsAdmin(sessionId))
			{
			    logger.info("not appropriate permission for saving a test case. Need to have admin role");
			    return Response.status(Response.Status.UNAUTHORIZED).build();
			}
		} catch (AuthenticationException e) {
		    return Response.status(e.getErrorStatus()).build();
		}
        logger.info("OK you have permission.");
		TestsService.INSTANCE.exportAllTests();
		return Response.ok().build();
	}

	@Path("techniques/{sessionId}")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public Response doTechniquesSpecSyncFromUrl(@PathParam("sessionId") String sessionId, String url) {
		try {
			if (!TestingSessionService.INSTANCE.isAuthenticatedAsAdmin(sessionId))
			{
			    logger.info("not appropriate permission for doTechniquesSpecSyncFromUrl");
			    return Response.status(Response.Status.UNAUTHORIZED).build();
			}
		} catch (AuthenticationException e) {
		    return Response.status(e.getErrorStatus()).build();
		}
		url = url.trim().replace("\"", "");
        logger.info("OK you have permission.");  
        logger.info("techniques url: " + url);
		try{
			List<Technique> l = TechniquesSpecParser.parse(url);
			return Response.ok(l).build();
		}
		catch(Exception e){
			logger.error(e.getLocalizedMessage());
			return Response.serverError().entity(e.getLocalizedMessage()).build();
		}
	}
	@Path("tests/deepdelete/{sessionId}/{nameid}")
	@DELETE
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteDeepTest(@PathParam("sessionId") String sessionId, @PathParam("nameid") String nameid){
		try {
			if (!TestingSessionService.INSTANCE.isAuthenticatedAsAdmin(sessionId))
			{
			    logger.info("not appropriate permission for doTechniquesSpecSyncFromUrl");
			    return Response.status(Response.Status.UNAUTHORIZED).build();
			}
		} catch (AuthenticationException e) {
		    return Response.status(e.getErrorStatus()).build();
		}
		try{
			TestsService.INSTANCE.deleteDeepTestUnitById(nameid);
		}
		catch(Exception e)
		{
			logger.warn(e.getLocalizedMessage());
			return Response.notModified().build(); 
		}		
		return Response.ok().build();
	}
	
	/*@Path("tests/deepdeletelist/{sessionId}")
	@DELETE
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteDeepTests(@PathParam("sessionId") String sessionId, List<String> nameids){
		if (!TestingSessionService.INSTANCE.isAuthenticatedAsAdmin(sessionId))
        {
            logger.info("not appropriate permission for doTechniquesSpecSyncFromUrl");
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } 
		try{
			for (String nameId : nameids) {
				TestsService.INSTANCE.deleteDeepTestUnitById(nameId);
			}
		}
		catch(Exception e)
		{
			logger.warn(e.getLocalizedMessage());
			return Response.notModified().build(); 
		}		
		return Response.ok().build();
	}*/
	
	@Path("techniques/deepdelete/{sessionId}/{nameid}")
	@DELETE
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteDeepTechnique(@PathParam("sessionId") String sessionId, @PathParam("nameid") String nameid){
		try {
			if (!TestingSessionService.INSTANCE.isAuthenticatedAsAdmin(sessionId))
			{
			    logger.info("not appropriate permission for doTechniquesSpecSyncFromUrl");
			    return Response.status(Response.Status.UNAUTHORIZED).build();
			}
		} catch (AuthenticationException e) {
		    return Response.status(e.getErrorStatus()).build();
		}
		Technique t = null;
		try{
			t = TechniquesService.INSTANCE.deleteTestsAndResultsByTechniqueNameId(nameid);
		}
		catch(Exception e)
		{
			logger.warn(e.getLocalizedMessage());
			return Response.notModified().entity(t).build(); 
		}		
		return Response.ok(t).build();
	}

	@Path("echo")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response echo() {
		return Response.ok("echo!!").build();
	}

}
