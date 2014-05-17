package org.w3c.wai.accessdb.sync.om;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.w3c.wai.accessdb.om.TestResultsBunch;
import org.w3c.wai.accessdb.utils.DateAdapter;

@XmlRootElement(name="ExportTestResultsFile")
@XmlAccessorType(XmlAccessType.FIELD)

public class ExportTestResultsFile{
	@XmlJavaTypeAdapter(DateAdapter.class)
	@XmlElement(name = "exportDate")
	private Date created;
	@XmlElementWrapper(name="bunchs")
	@XmlElement(name = "bunch")
	private List<TestResultsBunch> testResultsBunch = new ArrayList<TestResultsBunch>();
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public List<TestResultsBunch> getTestResultsBunch() {
		return testResultsBunch;
	}
	public void setTestResultsBunch(List<TestResultsBunch> testResultsBunch) {
		testResultsBunch = testResultsBunch;
	}
	
}
