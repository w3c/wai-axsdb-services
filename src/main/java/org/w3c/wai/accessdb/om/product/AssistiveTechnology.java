package org.w3c.wai.accessdb.om.product;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 25.01.12
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class AssistiveTechnology extends Product<AssistiveTechnology>
{

    public AssistiveTechnology(String name, String ver)
    {
        super(name, ver);
    }
    public AssistiveTechnology(Product p)
    {
        super(p.getName(), p.getVersion().getText());
    }

    public AssistiveTechnology()
    {
        super();
    }
    

}