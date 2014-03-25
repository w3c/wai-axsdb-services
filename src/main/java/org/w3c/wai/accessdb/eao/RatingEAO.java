package org.w3c.wai.accessdb.eao;

import java.util.List;

import org.w3c.wai.accessdb.om.Rating;


/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 12.05.12
 */ 

public class RatingEAO extends BaseEAO<Rating>
{
    
	 
	public List<Rating> getRatingsByRatedId(String ratedId)
    {
    	return this.findByNamedQuery("Rating.RatingsByRatedId", Long.parseLong(ratedId));
    }
	
	//"select avg (ratingValue) from Rating rating where..";
	public double getAverageByRatedId(String ratedId)
    {
		String s = String.valueOf(this.doNamedQuery("Rating.AverageByRatedId", Long.parseLong(ratedId)).get(0));
    	return Double.parseDouble(s);
    }
    /* (non-Javadoc)
     * @see org.w3c.wai.eao.BaseEAO#getEAOClass()
     */
    @Override
    public Class<? extends Rating> getEntityClass()
    {
        return Rating.class;
    }  
  
}
