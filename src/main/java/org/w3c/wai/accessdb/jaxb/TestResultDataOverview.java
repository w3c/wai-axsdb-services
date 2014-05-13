package org.w3c.wai.accessdb.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestResultDataOverview
{
    private String technique = "undefined";
    private String techniqueTitle = null;
    private String noOfPass = "undefined";
    private String noOfAll = "undefined";


    public String getTechnique()
    {
        return technique;
    }

    public void setTechnique(String technique)
    {
        this.technique = technique;
    }

    public String getNoOfPass()
    {
        return noOfPass;
    }

    public void setNoOfPass(String noOfPass)
    {
        this.noOfPass = noOfPass;
    }

    public String getNoOfAll()
    {
        return noOfAll;
    }

    public void setNoOfAll(String noOfAll)
    {
        this.noOfAll = noOfAll;
    }

    @Override
    public String toString()
    {
        return this.technique + " / " +  this.noOfAll + " / " +  this.noOfPass + " / ";
    }

	public String getTechniqueTitle() {
		return techniqueTitle;
	}

	public void setTechniqueTitle(String techniqueTitle) {
		this.techniqueTitle = techniqueTitle;
	}
    
}
