package org.w3c.wai.accessdb.sync;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.w3c.wai.accessdb.om.TestResult;
import org.w3c.wai.accessdb.utils.DateAdapter;

@XmlRootElement(name="testResults")
@XmlAccessorType(XmlAccessType.FIELD)

public class ExportTestResultsFile{
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date created;
	@XmlElementWrapper(name="testResults")
	@XmlElement(name = "testResult")
	private List<TestResult> testResults = new ArrayList<TestResult>();
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public List<TestResult> gettestResults() {
		return testResults;
	}
	public void settestResults(List<TestResult> testResults) {
		this.testResults = testResults;
	}
	 
}
