package org.w3c.wai.accessdb.jaxb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.w3c.wai.accessdb.om.TestResult;
import org.w3c.wai.accessdb.om.TestResultsBunch;
import org.w3c.wai.accessdb.utils.DateAdapter;
@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
public class SimpleTestResultsBunch {
	private String optionalName = null;
	@XmlElementWrapper(name="results")
	@XmlElement(name = "result")
	private List<SimpleTestResult> results  = new ArrayList<SimpleTestResult>();
	@XmlJavaTypeAdapter(DateAdapter.class)
    @XmlSchemaType(name = "date")
    private Date date;
    private String userId;
    
    public SimpleTestResultsBunch() {
	}
    public SimpleTestResultsBunch(TestResultsBunch b) {
    	this.optionalName = b.getOptionalName();
    	this.date = b.getDate();
    	this.userId = b.getUser().getUserId();
    	for (TestResult r : b.getResults()) {
			SimpleTestResult sr = new SimpleTestResult(r, b.getUser().getUserId());
			this.getResults().add(sr);
		}
	}    
	public String getOptionalName() {
		return optionalName;
	}
	public void setOptionalName(String optionalName) {
		this.optionalName = optionalName;
	}
	public List<SimpleTestResult> getResults() {
		return results;
	}
	public void setResults(List<SimpleTestResult> results) {
		this.results = results;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
