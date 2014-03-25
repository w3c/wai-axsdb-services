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

import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.jaxb.ElementWrapper;
import org.w3c.wai.accessdb.om.Guideline;
import org.w3c.wai.accessdb.om.Principle;
import org.w3c.wai.accessdb.om.SuccessCriterio;
import org.w3c.wai.accessdb.om.Technique;
import org.w3c.wai.accessdb.services.TechniquesService;
import org.w3c.wai.accessdb.services.WCAG2Service;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;

/**
 * @author evangelos.vlachogiannis
 * @since 16.04.12
 */ 
@Path("wcag2")
public class WCAG2Resource
{
    @Path("browse/wcag2/tree/{level}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getWCAGTreeData(@PathParam("level") String level)
    {
        return Response.ok(
        		WCAG2Service.INSTANCE.getSimpleWCAGTreeData(level))
                .build();
    }
    @Path("browse/webtechs/tree")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getWCAGTechs()
    {
        return Response.ok(
                TechniquesService.INSTANCE.getWebTechnologiesTree()).build();
    }
    @Path("browse/webtechswithtechniques/tree")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getWebTechnologiesWithTechniquesTree()
    {
        return Response.ok(
                TechniquesService.INSTANCE.getWebTechnologiesWithTechniquesTree()).build();
    }
    @Path("browse/byquery/{q}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getRequirementByQuery(@PathParam("q") String q)
    {
        List res = new ArrayList();
        try
        {
            q = URLDecoder.decode(q, "UTF-8");
        }
        catch (UnsupportedEncodingException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try
        {
            res = EAOManager.INSTANCE.getObjectEAO().doSimpleSelectOnlyQuery(q);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Response.serverError().build();
        }
        return Response.ok(new ElementWrapper(res)).build();
    }

    @Path("techniques/{type}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public ElementWrapper<Technique> getTechniques(
            @PathParam("type") String techTypeS) throws ASBPersistenceException
    {
        return new ElementWrapper<Technique>(
                TechniquesService.INSTANCE.retrieveTechniques(techTypeS));
    }

    @Path("techniques")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public ElementWrapper<Technique> getTechniques()
    {
        return new ElementWrapper<Technique>(EAOManager.INSTANCE
                .getTechniqueEAO().findAll());
    }

    @Path("/technique/{type}/byid/{id}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Technique getTechniqueById(@PathParam("type") String techTypeS,
            @PathParam("id") String id)
    {
    	try{
            return EAOManager.INSTANCE.getTechniqueEAO().findById(Long.parseLong(id));    		
    	}
    	catch(Exception e){
    		return null;
    	}
    }

    @GET
    @Path("/techniques/byterm/{term}")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<Technique> getTechniqueByTerm(@PathParam("term") String term)
    {
        return TechniquesService.INSTANCE.getTechniquesByTerm(term);
    }
    
    

    @Path("principles")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public ElementWrapper<Principle> getPrinciples()
    {
        return new ElementWrapper<Principle>(EAOManager.INSTANCE
                .getPrincipleEAO().findAll());
    }

    @Path("guidelines")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public ElementWrapper<Guideline> getGuidelines()
    {
        return new ElementWrapper<Guideline>(EAOManager.INSTANCE
                .getGuidelineEAO().findAll());
    }

    @Path("sc")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public ElementWrapper<SuccessCriterio> getSC()
    {
        return new ElementWrapper<SuccessCriterio>(EAOManager.INSTANCE
                .getCriterioEAO().findAll());
    }

    @GET
    @Path("/criteria")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<SuccessCriterio> getCriteria()
    {
        return EAOManager.INSTANCE.getCriterioEAO().findAll();

    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("/echo/{msg}")
    public String echoService(@PathParam("msg") String msg)
    {
        return msg;
    }

}
