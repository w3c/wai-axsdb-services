package org.w3c.wai.accessdb.eao;

import java.util.List;

import org.w3c.wai.accessdb.jaxb.TechnologyCombination;
import org.w3c.wai.accessdb.om.UserTestingProfile;



/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 12.05.12
 */ 

public class UserTestingProfileEAO extends BaseEAO<UserTestingProfile>
{
    
	public List<String> getAssistiveTechnologies(String term)
	{
		return ((List<String>)this.doNamedQuery("TestingProfile.at","%"+term+"%"));
	}
	public List<String> getPlatforms(String term)
	{
		return (List<String>)this.doNamedQuery("TestingProfile.platforms","%"+term+"%");
	}
	public List<String> getUserAgents(String term)
	{
		return (List<String>)this.doNamedQuery("TestingProfile.ua","%"+term+"%");
	}
	public List<String> getPlugins(String term)
	{
		return (List<String>)this.doNamedQuery("TestingProfile.plugins","%"+term+"%");
	}
   
    /* (non-Javadoc)
     * @see org.w3c.wai.eao.BaseEAO#getEAOClass()
     */
    @Override
    public Class<? extends UserTestingProfile> getEntityClass()
    {
        return UserTestingProfile.class;
    }
    
  
}
