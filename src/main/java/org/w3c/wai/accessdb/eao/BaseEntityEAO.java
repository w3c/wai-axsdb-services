package org.w3c.wai.accessdb.eao;

import org.w3c.wai.accessdb.om.base.BaseEntity;


/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 12.05.12
 */

public class BaseEntityEAO extends BaseEAO<BaseEntity>
{
    
	 
   

    /* (non-Javadoc)
     * @see org.w3c.wai.eao.BaseEAO#getEAOClass()
     */
    @Override
    public Class<? extends BaseEntity> getEntityClass()
    { 
        return BaseEntity.class;
    }
    
  
}
