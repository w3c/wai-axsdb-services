package org.w3c.wai.accessdb.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.w3c.wai.accessdb.jaxb.LoginData;
import org.w3c.wai.accessdb.jaxb.TestingSession;
import org.w3c.wai.accessdb.services.DBInitService;
import org.w3c.wai.accessdb.services.TestingSessionService;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;

/**
 * @author evangelos.vlachogiannis
 * @since 03.07.12
 */
@Path("testingsession")
public class TestingSessionResource {
	@POST
	@Path("commit")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response putSession(TestingSession session) {
		// System.out.print(session.getSessionId());
		session = TestingSessionService.INSTANCE.putSession(session);
		return Response.status(201).entity(session).build();
	}

	@Path("browse/{sessionid}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public TestingSession getSession(@PathParam("sessionid") String sessionid) {
		return TestingSessionService.INSTANCE.getSession(sessionid);
	}

	@POST
	@Path("login")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public TestingSession login(LoginData  data) throws ASBPersistenceException {
		return TestingSessionService.INSTANCE.login(data);
	}
	
	@POST
	@Path("logout/{sessionId}")
	public Response logout(@PathParam("sessionId") String sessionId) {
		TestingSessionService.INSTANCE.removeSession(sessionId);
		return Response.noContent().build();
	}
	
	@POST
	@Path("commit/persist/{sessionId}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public TestingSession save(@PathParam("sessionId") String sessionId) {
		return TestingSessionService.INSTANCE.saveSessionData(sessionId);
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })	
	@Path("/init")
	public void initService() throws ASBPersistenceException
	{
		DBInitService.INSTANCE.initAll();
	}
}
