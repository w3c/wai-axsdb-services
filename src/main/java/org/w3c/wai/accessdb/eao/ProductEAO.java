package org.w3c.wai.accessdb.eao;

import org.w3c.wai.accessdb.om.product.Product;


/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 12.05.12
 */

public class ProductEAO extends BaseEAO<Product>
{

    @Override
    public Class<? extends Product> getEntityClass()
    {
        return Product.class;
    }
    
}
