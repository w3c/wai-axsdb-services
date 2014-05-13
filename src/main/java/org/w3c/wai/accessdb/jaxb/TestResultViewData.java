package org.w3c.wai.accessdb.jaxb;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.w3c.wai.accessdb.om.TestResult;
import org.w3c.wai.accessdb.utils.DateAdapter;
import org.w3c.wai.accessdb.utils.JAXBUtils;
@XmlRootElement 
public class TestResultViewData
{
    public TestResultViewData()
    {
    }
    public TestResultViewData(Object[] o, String techId)
    {
        this.techniqueNameId = techId;
        this.testResult = (TestResult) o[0];
        String userId = (String) o[1];
        String optionalName = (String) o[2];
        if(userId!=null)
            this.contributor = userId;
        else if(optionalName!=null)
            this.contributor = optionalName;
        else
            this.contributor = "Anonymous";
        this.date = (Date) o[3];
    }
    private String techniqueNameId = "undefined";
    private TestResult testResult = null;
    private String contributor = "undefined";
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date date = null;
    public Date getDate()
    {
        return date;
    }
    public void setDate(Date date)
    {
        this.date = date;
    }
    public String getTechniqueNameId()
    {
        return techniqueNameId;
    }
    public void setTechniqueNameId(String techniqueNameId)
    {
        this.techniqueNameId = techniqueNameId;
    }
    public TestResult getTestResult()
    {
        return testResult;
    }
    public void setTestResult(TestResult testResult)
    {
        this.testResult = testResult;
    }
    public String getContributor()
    {
        return contributor;
    }
    public void setContributor(String contributor)
    {
        this.contributor = contributor;
    }
    @Override
    public String toString()
    {
        return JAXBUtils.objectToJSONString(this);
    }
}
