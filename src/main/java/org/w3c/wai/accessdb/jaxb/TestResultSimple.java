package org.w3c.wai.accessdb.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.wai.accessdb.om.TestingProfile;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class TestResultSimple {
	private TestingProfile testingProfile;
	private String testUnitId;
	private String resultValue;

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

	public String getResultValue() {
		return resultValue;
	}

	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}

}
