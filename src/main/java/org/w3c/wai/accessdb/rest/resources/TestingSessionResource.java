package org.w3c.wai.accessdb.rest.resources;

import javax.ws.rs.Consumes;
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
import org.w3c.wai.accessdb.jaxb.LoginData;
import org.w3c.wai.accessdb.jaxb.TestingSession;
import org.w3c.wai.accessdb.services.TestingSessionService;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;

/**
 * @author evangelos.vlachogiannis
 * @since 03.07.12
 */
@Path("testingsession")
public class TestingSessionResource {
	final static Logger logger = LoggerFactory
            .getLogger(TestingSessionResource.class);
	
	/**
	 * Add a session to the server side session pool. If there is no session id an id is being generated
	 * @param session
	 * @return
	 */
	
	@POST
	@Path("commit")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response save(TestingSession session) {
		try{
			session = TestingSessionService.INSTANCE.putSession(session);
			return Response.status(201).entity(session).build();
		}
		catch(Exception e){
			return Response.status(Status.NOT_MODIFIED).entity(session).build();
		}
	}

	/**
	 * Gets a session object by session id or create a new of not exists.
	 * @param sessionid
	 * @return
	 */
	@Path("browse/{sessionid}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSession(@PathParam("sessionid") String sessionid) {
		TestingSession s = null;
		logger.info("getSession");
		try{
			s = TestingSessionService.INSTANCE.getSession(sessionid);			
		}
		catch(Exception e){
			logger.error(e.getLocalizedMessage());
		}
		if(s==null){
			s = TestingSessionService.INSTANCE.createNewSession();
		}
		boolean isAuthenticated = TestingSessionService.INSTANCE.isAuthenticated(s.getSessionId());
		if(!isAuthenticated){
			return Response.status(Status.FORBIDDEN).entity(s).build();			
		}
		else{
			return Response.status(Status.OK).entity(s).build();
		}
	}
	/**
	 * Authentication / Authorization of the session
	 * @param data
	 * @return
	 */
	@POST
	@Path("login")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response login(LoginData  data) {
		TestingSession s = null;
		if(data.getSessionId()==null){
			s = TestingSessionService.INSTANCE.createNewSession();
			data.setSessionId(s.getSessionId());
		}
		if(!data.isValid())
			return Response.status(Status.NOT_ACCEPTABLE).entity(data).build();
		try {
			s = TestingSessionService.INSTANCE.login(data);
		} catch (ASBPersistenceException e) {
			logger.warn(e.getLocalizedMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(s).build();
		}
		if(s.getUserId()!=null)
			return Response.status(Status.OK).entity(s).build();
		else
			return Response.status(Status.FORBIDDEN).entity(s).build();
	}
	/**
	 * Session log out
	 * @param sessionId
	 * @return
	 */
	@POST
	@Path("logout/{sessionId}")
	public Response logout(@PathParam("sessionId") String sessionId) {
		TestingSessionService.INSTANCE.removeSession(sessionId);
		return Response.status(Status.OK).build();
	}
	/**
	 * Given a session, this persists the content (test results, [TODO: search filters]) in the database.
	 * @param sessionId
	 * @return
	 */
	@POST
	@Path("commit/persist/{sessionId}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response persist(@PathParam("sessionId") String sessionId) {
		TestingSession s = null;
		try {
			s = TestingSessionService.INSTANCE.saveSessionData(sessionId);
		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(s).build();
		}
		return Response.status(Status.OK).entity(s).build();
	}
}
