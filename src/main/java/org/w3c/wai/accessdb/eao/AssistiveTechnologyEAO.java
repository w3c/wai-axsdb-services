package org.w3c.wai.accessdb.eao;

import org.w3c.wai.accessdb.om.product.AssistiveTechnology;


/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 12.05.12
 */

public class AssistiveTechnologyEAO extends BaseEAO<AssistiveTechnology>
{ 	
    @Override
    public Class<? extends AssistiveTechnology> getEntityClass()
    {
        return AssistiveTechnology.class;
    }
}
