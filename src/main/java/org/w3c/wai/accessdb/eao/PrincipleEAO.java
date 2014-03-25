package org.w3c.wai.accessdb.eao;

import org.w3c.wai.accessdb.om.Principle;

/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 12.05.12
 */
 
public class PrincipleEAO extends BaseEAO<Principle>
{
    
    /* (non-Javadoc)
     * @see org.w3c.wai.eao.BaseEAO#getEAOClass()
     */
    @Override
    public Class getEntityClass()
    {
        return Principle.class;
    }
    
  
}
