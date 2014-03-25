package org.w3c.wai.accessdb.eao;

import java.util.List;

import org.w3c.wai.accessdb.om.Technique;

/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 12.05.12
 */

public class TechniqueEAO extends BaseEAO<Technique>
{
    
    public List<Technique> getRequirementsByTerm(String term)
    {
    	return this.findByNamedQuery("Technique.byTerm", "%"+term+"%", "%"+term+"%");
    }	
    public Technique findByNameId(String nameId)
    {
    	List<Technique> l = this.findByNamedQuery("Technique.byNameId", nameId);
    	if(l.size()>0)
    		return l.get(0);
    	return null;
    }
    public List<Technique> findByWebTechNameId(String webTechNameId)
    {
    	return this.findByNamedQuery("Technique.findByWebTechNameId", webTechNameId);
    }	

    /* (non-Javadoc)
     * @see org.w3c.wai.eao.BaseEAO#getEAOClass()
     */
    @Override
    public Class getEntityClass()
    {
        return Technique.class;
    }
    
  
}
