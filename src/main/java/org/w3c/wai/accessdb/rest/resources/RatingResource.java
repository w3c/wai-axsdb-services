package org.w3c.wai.accessdb.rest.resources;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.jaxb.ElementWrapper;
import org.w3c.wai.accessdb.om.Rating;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;

/**
 * @author evangelos.vlachogiannis
 * @since 30.05.12
 */
@Path("rating")
public class RatingResource
{

    @Path("commit")
    @POST
    public void rate(@FormParam("ratingValue") String ratingValue,
            @FormParam("ratedType") String ratedType,
            @FormParam("ratedId") String ratedId) throws ASBPersistenceException
    {
        if (ratingValue != null && ratedId != null)
        {
            Rating rating = new Rating();
            rating.setRatingValue(Integer.parseInt(ratingValue));
            rating.setRatedType(ratedType);
            rating.setRatedId(Long.parseLong(ratedId));
            EAOManager.INSTANCE.getRatingEAO().persist(rating);
        }

    }
    @Path("browse/{ratedId}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public ElementWrapper<Rating> findRatingsByRatedId(
            @PathParam("ratedId") String ratedId)
    {
        return new ElementWrapper<Rating>(EAOManager.INSTANCE.getRatingEAO()
                .getRatingsByRatedId(ratedId));
    }
    @Path("browse/calculate/{ratedId}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public String calculateRatingByRatedId(@PathParam("ratedId") String ratedId)
    {
        int no = EAOManager.INSTANCE.getRatingEAO()
                .getRatingsByRatedId(ratedId).size();
        if (no > 0)
            return String.valueOf(EAOManager.INSTANCE.getRatingEAO()
                    .getAverageByRatedId(ratedId));
        else
            return "";
    }
}
