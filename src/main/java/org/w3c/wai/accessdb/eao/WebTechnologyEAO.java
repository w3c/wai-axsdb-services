package org.w3c.wai.accessdb.eao;

import java.util.List;

import org.w3c.wai.accessdb.om.WebTechnology;

/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 12.05.12
 */

public class WebTechnologyEAO extends BaseEAO<WebTechnology>
{
    
	 
    public WebTechnology findByNameId(String nameId)
    {
    	List<WebTechnology> t = this.findByNamedQuery("WebTechnology.findbyNameId", nameId);
    	if(t.size()>0)
    		return (WebTechnology) this.findByNamedQuery("WebTechnology.findbyNameId", nameId).get(0);
    	else
    		return null;
    }
  
    /* (non-Javadoc)
     * @see org.w3c.wai.eao.BaseEAO#getEAOClass()
     */
    @Override
    public Class getEntityClass()
    {
        return WebTechnology.class;
    }
    
  
}
