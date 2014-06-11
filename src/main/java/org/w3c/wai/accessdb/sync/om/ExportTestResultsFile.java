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

import org.w3c.wai.accessdb.jaxb.SimpleTestResultsBunch;
import org.w3c.wai.accessdb.utils.DateAdapter;

@XmlRootElement(name="ExportTestResultsFile")
@XmlAccessorType(XmlAccessType.FIELD)

public class ExportTestResultsFile{
	@XmlJavaTypeAdapter(DateAdapter.class)
	@XmlElement(name = "exportDate")
	private Date created;
	@XmlElementWrapper(name="bunchs")
	@XmlElement(name = "bunch")
	private List<SimpleTestResultsBunch> testResultsBunch = new ArrayList<SimpleTestResultsBunch>();
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public List<SimpleTestResultsBunch> getTestResultsBunch() {
		return testResultsBunch;
	}
	public void setTestResultsBunch(List<SimpleTestResultsBunch> testResultsBunch) {
		this.testResultsBunch = testResultsBunch;
	}
	
	
	
}
