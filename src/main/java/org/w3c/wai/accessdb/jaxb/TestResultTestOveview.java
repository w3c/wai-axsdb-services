package org.w3c.wai.accessdb.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestResultTestOveview {
    private String noOfPass = "undefined";
    private String noOfAll =  "undefined";
    private String testUnitId = null;
    private String testTitle = null;

	public String getNoOfPass() {
		return noOfPass;
	}
	public void setNoOfPass(String noOfPass) {
		this.noOfPass = noOfPass;
	}
	public String getNoOfAll() {
		return noOfAll;
	}
	public void setNoOfAll(String noOfAll) {
		this.noOfAll = noOfAll;
	}
	public String getTestTitle() {
		return testTitle;
	}
	public void setTestTitle(String testTitle) {
		this.testTitle = testTitle;
	}
	public String getTestUnitId() {
		return testUnitId;
	}
	public void setTestUnitId(String testUnitId) {
		this.testUnitId = testUnitId;
	}
    
}
