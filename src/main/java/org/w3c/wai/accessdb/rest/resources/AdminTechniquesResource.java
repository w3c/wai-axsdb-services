package org.w3c.wai.accessdb.rest.resources;

import java.util.List;

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
import org.w3c.wai.accessdb.om.Technique;
import org.w3c.wai.accessdb.services.TestingSessionService;
import org.w3c.wai.accessdb.sync.GitHubTechniquesSpecParser;
import org.w3c.wai.accessdb.sync.om.GitHubTechniqueInfo;
import org.w3c.wai.accessdb.sync.om.ImportResponse;
import org.w3c.wai.accessdb.utils.AuthenticationException;

/**
 * @author evangelos.vlachogiannis
 * @since 30.05.12
 */
@Path("admin/techniques")
public class AdminTechniquesResource {
	final static Logger logger = LoggerFactory
			.getLogger(AdminTechniquesResource.class);

	@Path("{sessionId}/import-prepare")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public Response importPrepare(@PathParam("sessionId") String sessionId,
			String url) {
		try {
			if (!TestingSessionService.INSTANCE
					.isAuthenticatedAsAdmin(sessionId)) {
				logger.info("not appropriate permission for doTechniquesSpecSyncFromUrl");
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}
		} catch (AuthenticationException e) {
			return Response.status(e.getErrorStatus()).build();
		}
		url = url.trim().replace("\"", "");
		logger.debug("OK you have permission.");
		logger.debug("techniques url: " + url);
		try {
			ImportResponse<List<GitHubTechniqueInfo>> allInGit = GitHubTechniquesSpecParser
					.prepareImport(url);
			if(allInGit.getStatusCode() != ImportResponse.OK){
				logger.debug("ImportResponse REST failed");
				return Response.status(allInGit.getStatusCode()).build();
			}
			logger.debug("all : " + allInGit.getEntity().size());
			List<ImportResponse<GitHubTechniqueInfo>> filtered = GitHubTechniquesSpecParser
					.filterTechniques(allInGit);
			logger.debug("filtered : " + filtered.size());
			return Response.ok(filtered).build();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return Response.serverError().entity(e.getLocalizedMessage())
					.build();
		}
	}
	@Path("{sessionId}/import-do")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public Response importDo(@PathParam("sessionId") String sessionId,
			List<ImportResponse<GitHubTechniqueInfo>> toImport) {
		try {
			if (!TestingSessionService.INSTANCE
					.isAuthenticatedAsAdmin(sessionId)) {
				logger.info("not appropriate permission for doTechniquesSpecSyncFromUrl");
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}
		} catch (AuthenticationException e) {
			return Response.status(e.getErrorStatus()).build();
		}
		logger.debug("OK you have permission.");
		try {
			logger.debug("techniques url: " + toImport.size());
			if(toImport.size()>0){
				List<ImportResponse<Technique>> rs = GitHubTechniquesSpecParser.importTechniques(toImport);
				return Response.status(Status.CREATED).entity(rs).build();
			}
			else{
				return Response.noContent().build();
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return Response.serverError().entity(e.getLocalizedMessage())
					.build();
		}
	}

	@Path("echo")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response echo() {
		return Response.ok("echo!!").build();
	}

}
