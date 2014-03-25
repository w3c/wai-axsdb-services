package org.w3c.wai.accessdb.eao;

import java.util.List;

import org.w3c.wai.accessdb.om.SuccessCriterio;

/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 12.05.12
 */

public class SuccessCriterioEAO extends BaseEAO<SuccessCriterio>
{ 
    
   
	public SuccessCriterio getByIdRef(String idRef)
    {
		List<SuccessCriterio> l = this.findByNamedQuery("SuccessCriterio.findbyIdRef", idRef);
		if(l.size()>0)
			return l.get(0);
		return null;
    }
	public List<SuccessCriterio> findByTechniqueNameId(String nameId)
    {
		return this.findByNamedQuery("SuccessCriterio.findByTechniqueNameId", nameId);
    }
    /* (non-Javadoc)
     * @see org.w3c.wai.eao.BaseEAO#getEAOClass()
     */
    @Override
    public Class getEntityClass()
    {
        return SuccessCriterio.class;
    }
    
  
}
