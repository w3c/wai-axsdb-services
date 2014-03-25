package org.w3c.wai.accessdb.eao;

import org.w3c.wai.accessdb.om.product.Platform;


/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 12.05.12
 */

public class PlatformEAO extends BaseEAO<Platform>
{
	
    @Override
    public Class<? extends Platform> getEntityClass()
    {
        return Platform.class;
    }
   
  
}
