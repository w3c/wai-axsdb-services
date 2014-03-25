package org.w3c.wai.accessdb.eao;

import java.util.List;

import org.w3c.wai.accessdb.om.SuccessCriterioTechniqueRelation;




public class SuccessCriterioTechniqueRelationEAO extends BaseEAO<SuccessCriterioTechniqueRelation>
{
    
    

    /* (non-Javadoc)
     * @see org.w3c.wai.eao.BaseEAO#getEAOClass()
     */
    @Override
    public Class getEntityClass()
    {
        return SuccessCriterioTechniqueRelation.class;
    }
    public List<SuccessCriterioTechniqueRelation> findByTechniqueNameId(String nameId){
    	return this.findByNamedQuery("SuccessCriterioTechniqueRelation.findByTechniqueNameId", nameId);
    }
  
}
