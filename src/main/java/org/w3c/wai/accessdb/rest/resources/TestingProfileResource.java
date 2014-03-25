package org.w3c.wai.accessdb.rest.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.jaxb.ElementWrapper;
import org.w3c.wai.accessdb.om.Header;
import org.w3c.wai.accessdb.om.User;
import org.w3c.wai.accessdb.om.UserTestingProfile;
import org.w3c.wai.accessdb.services.TestingSessionService;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;

@Path("profile")
public class TestingProfileResource
{
    final static Logger logger = LoggerFactory
            .getLogger(TestingProfileResource.class);

    @Path("{userId}/{sessionId}")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    public Response insertTestingProfile(UserTestingProfile p,
            @PathParam("userId") String userId,
            @PathParam("sessionId") String sessionId) throws ASBPersistenceException
    {
        if (!TestingSessionService.INSTANCE.isAuthenticated(sessionId))
        {
            logger.info("not logged in to save user profile");
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        User user = EAOManager.INSTANCE.getUserEAO().findByUserId(userId);
        if (user == null)
            return Response.noContent().status(Response.Status.NOT_FOUND)
                    .build();
        EAOManager.INSTANCE.getUserTestingProfileEAO().persist(p); 
        user.getUserTestingProfiles().add(p);
        user = EAOManager.INSTANCE.getUserEAO().persist(user);
        if (p.getId() > 0)
            return Response.created(URI.create(String.valueOf(p.getId())))
                    .build();
        else
            return Response.notModified(String.valueOf(p.getId())).build();
    }

    @Path("{sessionId}")
    @PUT
    @Produces({ MediaType.APPLICATION_JSON })
    public Response updateTestingProfile(UserTestingProfile p,
            @PathParam("sessionId") String sessionId)
    {
        if (!TestingSessionService.INSTANCE.isAuthenticated(sessionId))
        {
            logger.info("not logged in to update user profile");
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        if (p.getId() < 1)
            return Response.noContent().status(Response.Status.NOT_FOUND)
                    .build();
        try
        {
            EAOManager.INSTANCE.getUserTestingProfileEAO().persist(p);
            return Response.created(URI.create(String.valueOf(p.getId())))
                    .build();
        }
        catch (Exception e)
        {
            logger.error(e.getLocalizedMessage());
            return Response.notModified(String.valueOf(p.getId())).build();
        }
    }
    @Path("{pid}/{sessionId}")
    @DELETE
    @Produces({ MediaType.APPLICATION_JSON })
    public Response deleteTestingProfile(@PathParam("pid") String pid,@PathParam("sessionId") String sessionId)
    {
        if (!TestingSessionService.INSTANCE.isAuthenticated(sessionId))
        {
            logger.info("not logged in to delete user profile");
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        UserTestingProfile p = EAOManager.INSTANCE.getUserTestingProfileEAO().findById(Long.parseLong(pid));
        if (p==null)
            return Response.noContent().status(Response.Status.NOT_FOUND)
                    .build();
        String userId = TestingSessionService.INSTANCE.getSession(sessionId).getUserId();
        User user = EAOManager.INSTANCE.getUserEAO().findByUserId(userId);  
        int index = -1;
        int count = 0;
        for (UserTestingProfile profile : user.getUserTestingProfiles())
        {
            if(profile.getId() == p.getId())
            {
                index=count;
                break;
            }
            count++;
        }
        user.getUserTestingProfiles().remove(index);
        try 
        {
            EAOManager.INSTANCE.getUserEAO().persist(user);
            EAOManager.INSTANCE.getUserTestingProfileEAO().delete(p);
            return Response.ok().build();
        }
        catch (Exception e)
        {
            logger.error(e.getLocalizedMessage());
            return Response.notModified(String.valueOf(p.getId())).build();
        }
    }

    @Path("{userId}/{sessionId}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getProfilesByUserId(@PathParam("userId") String userId,
            @PathParam("sessionId") String sessionId)
    {
        if (!TestingSessionService.INSTANCE.isAuthenticated(sessionId))
        {
            logger.info("not logged in to update user profile");
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        if (userId == null)
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        User user = EAOManager.INSTANCE.getUserEAO().findByUserId(userId);
        if (user == null)
            return Response.noContent().status(Response.Status.NOT_FOUND)
                    .build();
        GenericEntity<List<UserTestingProfile>> entityList = new GenericEntity<List<UserTestingProfile>>(
                user.getUserTestingProfiles()){};
        return Response.ok(entityList).build();
    }

    @Path("browse/ATs/{term}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public ElementWrapper<String> getAssistiveTechnologies(
            @PathParam("term") String term)
    {
        return new ElementWrapper<String>(EAOManager.INSTANCE
                .getUserTestingProfileEAO().getAssistiveTechnologies(
                        "%" + term + "%"));
    }

    @Path("browse/platforms/{term}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public ElementWrapper<String> getPlatforms(@PathParam("term") String term)
    {
        return new ElementWrapper<String>(EAOManager.INSTANCE
                .getUserTestingProfileEAO().getPlatforms("%" + term + "%"));
    }

    @Path("browse/UAs/{term}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public ElementWrapper<String> getUserAgents(@PathParam("term") String term)
    {
        return new ElementWrapper<String>(EAOManager.INSTANCE
                .getUserTestingProfileEAO().getUserAgents("%" + term + "%"));
    }

    @Path("browse/Plugins/{term}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public ElementWrapper<String> getPlugins(@PathParam("term") String term)
    {
        return new ElementWrapper<String>(EAOManager.INSTANCE
                .getUserTestingProfileEAO().getPlugins("%" + term + "%"));
    }

    @Path("browse/{profileId}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public UserTestingProfile getProfileById(
            @PathParam("profileId") String profileId)
    {
        return EAOManager.INSTANCE.getUserTestingProfileEAO().findById(
                Long.parseLong(profileId));
    }

    @Deprecated
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("headers")
    public ElementWrapper<Header> echoHeaders(@Context HttpHeaders headers)
    {
        ElementWrapper<Header> wrapper = new ElementWrapper<Header>();
        List<Header> headersList = new ArrayList<Header>();
        MultivaluedMap<String, String> headersMap = headers.getRequestHeaders();
        Iterator<String> hIterator = headersMap.keySet().iterator();
        while (hIterator.hasNext())
        {
            String name = (String) hIterator.next();
            String value = headersMap.getFirst(name);
            headersList.add(new Header(name, value));
        }
        wrapper.setList(headersList);
        return wrapper;
    }
}
