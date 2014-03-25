package org.w3c.wai.accessdb.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestResultDataOverview
{
    private String technique = "undefined";

    private String noOfPass = "undefined";

    private String noOfAll = "undefined";

    private String noOfUniqueCombinations = "undefined";

    private String noOfUniqueContributors = "undefined";

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

    public String getNoOfUniqueCombinations()
    {
        return noOfUniqueCombinations;
    }

    public void setNoOfUniqueCombinations(String noOfUniqueCombinations)
    {
        this.noOfUniqueCombinations = noOfUniqueCombinations;
    }

    public String getNoOfUniqueContributors()
    {
        return noOfUniqueContributors;
    }

    public void setNoOfUniqueContributors(String noOfUniqueContributors)
    {
        this.noOfUniqueContributors = noOfUniqueContributors;
    }
    @Override
    public String toString()
    {
        // TODO Auto-generated method stub
        return this.technique + " / " +  this.noOfAll + " / " +  this.noOfPass + " / " +  this.noOfUniqueCombinations + " / " + this.noOfUniqueContributors;
    }
    
}
