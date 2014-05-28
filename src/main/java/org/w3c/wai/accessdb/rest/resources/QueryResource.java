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
	
	/**
	 * This is the general Query method. Here AxsDB select only HQL queries can be executed
	 * @param q
	 * @return
	 */
	@Path("{q}") 
	@GET	
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findByQuery(@PathParam("q") String q) {
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
	
	
}
