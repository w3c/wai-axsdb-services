package org.w3c.wai.accessdb.om.product;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class UAgent extends Product<UAgent>
{

    @Basic
    private String engine;

    public UAgent()
    {

    }
    public UAgent(Product p)
    {
        super(p.getName(), p.getVersion().getText());
    }
    public UAgent(String name, String ver)
    {
        super(name, ver);
    }

    public void setEngine(String param)
    {
        this.engine = param;
    }

    public String getEngine()
    {
        return engine;
    }

}