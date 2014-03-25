package org.w3c.wai.accessdb.eao;

import org.w3c.wai.accessdb.om.product.UAgent;

 
/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 12.05.12
 */

public class UAgentEAO extends BaseEAO<UAgent>
{
  
	
    @Override
    public Class<? extends UAgent> getEntityClass()
    {
        return UAgent.class;
    }
    
}
