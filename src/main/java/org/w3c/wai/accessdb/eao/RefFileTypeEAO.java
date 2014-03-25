package org.w3c.wai.accessdb.eao;

import org.w3c.wai.accessdb.om.product.Product;
import org.w3c.wai.accessdb.om.testunit.RefFileType;


/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 12.05.12
 */

public class RefFileTypeEAO extends BaseEAO<RefFileType>
{
    
	 
   

    /* (non-Javadoc)
     * @see org.w3c.wai.eao.BaseEAO#getEAOClass()
     */
    @Override
    public Class<? extends RefFileType> getEntityClass()
    {
        return RefFileType.class;
    }
    
  
}
