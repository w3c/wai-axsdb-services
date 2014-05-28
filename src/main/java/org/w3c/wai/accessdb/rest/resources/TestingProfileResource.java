package org.w3c.wai.accessdb.rest.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.jaxb.ElementWrapper;
import org.w3c.wai.accessdb.om.User;
import org.w3c.wai.accessdb.om.UserTestingProfile;
import org.w3c.wai.accessdb.services.TestingSessionService;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;

@Path("profile")
public class TestingProfileResource
{
    final static Logger logger = LoggerFactory
            .getLogger(TestingProfileResource.class);

    /**
     * Inserts in database a testing profile for the specific user
     * @param p
     * @param userId
     * @param sessionId
     * @return
     * @throws ASBPersistenceException
     */
    @Path("{userId}/{sessionId}")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    public Response insertUserProfile(UserTestingProfile p,
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

    /**
     * Updates in database a testing profile for the specific user
     * @param p
     * @param sessionId
     * @return
     */
    @Path("put/{sessionId}")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    public Response updateUserProfile(UserTestingProfile p,
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
    /**
     * Deletes in database a testing profile for the specific user
     * @param pid
     * @param sessionId
     * @return
     */
    @Path("{pid}/{sessionId}")
    @DELETE
    @Produces({ MediaType.APPLICATION_JSON })
    public Response deleteUserProfile(@PathParam("pid") String pid,@PathParam("sessionId") String sessionId)
    {
        if (!TestingSessionService.INSTANCE.isAuthenticated(sessionId))
        {
            logger.info("not logged in to delete user profile");
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        UserTestingProfile p = EAOManager.INSTANCE.getUserTestingProfileEAO().findById(Long.parseLong(pid));
        if (p==null)
            return Response.status(Response.Status.NOT_FOUND)
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

    /**
     * Get Profiles By UserId
     * @param userId
     * @param sessionId
     * @return
     */
    @Path("{userId}/{sessionId}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response findByUserId(@PathParam("userId") String userId,
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

    /**
     * Retrieve unique AssistiveTechnologies in existing testing profiles by term
     * @param term
     * @return
     */
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
    /**
     * Retrieve unique Platforms (OSs) in existing testing profiles by term
     * @param term
     * @return
     */
    @Path("browse/platforms/{term}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public ElementWrapper<String> getPlatforms(@PathParam("term") String term)
    {
        return new ElementWrapper<String>(EAOManager.INSTANCE
                .getUserTestingProfileEAO().getPlatforms("%" + term + "%"));
    }

    /**
     * Retrieve unique User Agents (browsers) in existing testing profiles by term
     * @param term
     * @return
     */
    @Path("browse/UAs/{term}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public ElementWrapper<String> getUserAgents(@PathParam("term") String term)
    {
        return new ElementWrapper<String>(EAOManager.INSTANCE
                .getUserTestingProfileEAO().getUserAgents("%" + term + "%"));
    }

    /**
     * 
     * @param term
     * @return
     */
    @Path("browse/Plugins/{term}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public ElementWrapper<String> getPlugins(@PathParam("term") String term)
    {
        return new ElementWrapper<String>(EAOManager.INSTANCE
                .getUserTestingProfileEAO().getPlugins("%" + term + "%"));
    }
    /**
     * Retrieve unique Plugins in existing testing profiles by term
     * @param profileId
     * @return
     */
    @Path("browse/{profileId}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public UserTestingProfile findByProfileId(
            @PathParam("profileId") String profileId)
    {
        return EAOManager.INSTANCE.getUserTestingProfileEAO().findById(
                Long.parseLong(profileId));
    }
}
