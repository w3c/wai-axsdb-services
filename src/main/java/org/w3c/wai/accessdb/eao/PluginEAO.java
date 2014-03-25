package org.w3c.wai.accessdb.eao;

import org.w3c.wai.accessdb.om.product.Plugin;

 
/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 12.05.12
 */

public class PluginEAO extends BaseEAO<Plugin>
{
   
	
    @Override
    public Class<? extends Plugin> getEntityClass()
    {
        return Plugin.class;
    }
    
}
