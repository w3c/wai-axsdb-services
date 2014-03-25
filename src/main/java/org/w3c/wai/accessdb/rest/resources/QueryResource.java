package org.w3c.wai.accessdb.rest.resources;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.jaxb.ElementWrapper;

/**
 * @author evangelos.vlachogiannis
 * @since 16.04.12
 */
@Path("query")
public class QueryResource {
	final static Logger logger = LoggerFactory.getLogger(QueryResource.class);

	@Path("{q}") 
	@GET	
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getResultByQuery(@PathParam("q") String q) {
		List res = new ArrayList();
		try {
			q = URLDecoder.decode(q,"UTF-8"); 
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		logger.debug(q);
		try{
			res = EAOManager.INSTANCE.getObjectEAO().doSimpleSelectOnlyQuery(q);	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Response.serverError().build();
		}
		logger.debug("Siye of result list: "+res.size());
		return Response.ok(new ElementWrapper(res)).build();
	}
	@Path("browse/byquery/{q}/first/{first}/limit/{limit}")
	@GET	
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getResultByQueryAndPaging(@PathParam("q") String q,@PathParam("first") String first,@PathParam("limit") String limit) {
		List res = new ArrayList();
		try {
			q = URLDecoder.decode(q,"UTF-8"); 
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{ 
			res = EAOManager.INSTANCE.getObjectEAO().doSimpleSelectOnlyQuery(q, Integer.parseInt(first), Integer.parseInt(limit));	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Response.serverError().build();
		}
		return Response.ok(new ElementWrapper(res)).build();
	}
	
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })	
	@Path("/echo/{msg}")
	public String echoService(@PathParam("msg") String msg)
	{
		return msg;
	}
	
	
}
