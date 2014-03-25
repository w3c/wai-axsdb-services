package org.w3c.wai.accessdb.eao;

import org.w3c.wai.accessdb.om.Guideline;

/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 12.05.12
 */
 
public class GuidelineEAO extends BaseEAO<Guideline>
{ 
    
    /* (non-Javadoc)
     * @see org.w3c.wai.eao.BaseEAO#getEAOClass()
     */
    @Override
    public Class getEntityClass()
    {
        return Guideline.class;
    }
    
  
}
