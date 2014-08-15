package org.w3c.wai.accessdb.jaxb;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.w3c.wai.accessdb.om.TestResult;
import org.w3c.wai.accessdb.om.TestingProfile;
import org.w3c.wai.accessdb.utils.DateAdapter;

@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
public class SimpleTestResult {  
    private TestingProfile testingProfile;
    private String testUnitId;
    private boolean resultValue;
    private String comment;
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlSchemaType(name = "date")
    private Date runDate;
    private String userId;
    private long resultId;
    
    
	public SimpleTestResult() {
		super();
	}
	public SimpleTestResult(TestResult r, String userId) {
		this.testingProfile = r.getTestingProfile();
		this.testUnitId = r.getTestUnitDescription().getTestUnitId();
		this.resultValue = r.isResultValue();
		this.comment = r.getComment();
		this.runDate = r.getRunDate();
		this.userId = userId;
	}
	
	public long getResultId() {
		return resultId;
	}
	public void setResultId(long resultId) {
		this.resultId = resultId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public TestingProfile getTestingProfile() {
		return testingProfile;
	}
	public void setTestingProfile(TestingProfile testingProfile) {
		this.testingProfile = testingProfile;
	}
	public String getTestUnitId() {
		return testUnitId;
	}
	public void setTestUnitId(String testUnitId) {
		this.testUnitId = testUnitId;
	}
	public boolean isResultValue() {
		return resultValue;
	}
	public void setResultValue(boolean resultValue) {
		this.resultValue = resultValue;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getRunDate() {
		return runDate;
	}
	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}
    
}
