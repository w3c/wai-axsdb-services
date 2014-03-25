package org.w3c.wai.accessdb.om.product;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class UAgent extends Product<UAgent>
{

    @Basic
    @XmlElement
    private String engine;

    public UAgent()
    {

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