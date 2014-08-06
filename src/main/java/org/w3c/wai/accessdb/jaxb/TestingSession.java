package org.w3c.wai.accessdb.jaxb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.wai.accessdb.om.UserTestingProfile;
import org.w3c.wai.accessdb.om.testunit.TestUnitDescription;
import org.w3c.wai.accessdb.utils.JAXBUtils;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class TestingSession{
	public String sessionName = null;
	private Date lastAccessed = new Date();
	private String sessionId = "";
	private String userId = null;
	private List<String> userRoles = new ArrayList<String>();
	private String testProfileId = "-1";
	private List<String> testUnitIdList = new ArrayList<String>();
	private List<TestResultSimple> testResultList = new ArrayList<TestResultSimple>();//TODO: remove
	private List<RatingSimple> ratings = new ArrayList<RatingSimple>();
	private List<UserTestingProfile> userTestingProfiles = new ArrayList<UserTestingProfile>();
	private String currentTestUnitId = "-1";
	
	private int pCounter;
	private TestUnitDescription lastTestUnit;
	private TestResultFilter resultsFilter = new TestResultFilter() ;
	private TestResultFilter testsFilter = new TestResultFilter();
	private String paramId;
	private TestResultViewData resultsviewdata;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getSessionName() {
		return sessionName;
	}
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	public List<UserTestingProfile> getUserTestingProfiles() {
		return userTestingProfiles;
	}
	public void setUserTestingProfiles(List<UserTestingProfile> userTestingProfiles) {
		this.userTestingProfiles = userTestingProfiles;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getTestProfileId() {
		return testProfileId;
	}
	public void setTestProfileId(String testProfileId) {
		this.testProfileId = testProfileId;
	}
	public List<String> getTestUnitIdList() {
		return testUnitIdList;
	}
	public void setTestUnitIdList(List<String> testUnitIdList) {
		this.testUnitIdList = testUnitIdList;
	}
	
	public List<TestResultSimple> getTestResultList() {
		return testResultList;
	}
	public void setTestResultList(List<TestResultSimple> testResultList) {
		this.testResultList = testResultList;
	}
	
	public List<RatingSimple> getRatings() {
		return ratings;
	}
	public void setRatings(List<RatingSimple> ratings) {
		this.ratings = ratings;
	}
	public String getCurrentTestUnitId() {
		return currentTestUnitId;
	}
	public void setCurrentTestUnitId(String currentTestUnitId) {
		this.currentTestUnitId = currentTestUnitId;
	}
	public Date getLastAccessed() {
		return lastAccessed;
	}
	public void setLastAccessed(Date lastAccessed) {
		this.lastAccessed = lastAccessed;
	}
	public List<String> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(List<String> userRoles) {
		this.userRoles = userRoles;
	}
	public int getpCounter()
    {
        return pCounter;
    }
    public void setpCounter(int pCounter)
    {
        this.pCounter = pCounter;
    }
    public TestUnitDescription getLastTestUnit()
    {
        return lastTestUnit;
    }
    public void setLastTestUnit(TestUnitDescription lastTestUnit)
    {
        this.lastTestUnit = lastTestUnit;
    }
    public TestResultFilter getResultsFilter()
    {
        return resultsFilter;
    }
    public void setResultsFilter(TestResultFilter resultsFilter)
    {
        this.resultsFilter = resultsFilter;
    }
    public TestResultFilter getTestsFilter()
    {
        return testsFilter;
    }
    public void setTestsFilter(TestResultFilter testsFilter)
    {
        this.testsFilter = testsFilter;
    }
    public String getParamId()
    {
        return paramId;
    }
    public void setParamId(String paramId)
    {
        this.paramId = paramId;
    }
    public TestResultViewData getResultsviewdata()
    {
        return resultsviewdata;
    }
    public void setResultsviewdata(TestResultViewData resultsviewdata)
    {
        this.resultsviewdata = resultsviewdata;
    }
    @Override
	public String toString() {
		return JAXBUtils.objectToJSONString(this);
	}
	
	
	
}
