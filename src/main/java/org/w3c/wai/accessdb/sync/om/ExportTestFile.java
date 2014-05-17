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

import org.w3c.wai.accessdb.utils.DateAdapter;

@XmlRootElement(name="testsSet")
@XmlAccessorType(XmlAccessType.FIELD)

public class ExportTestFile{
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date created;
	@XmlElementWrapper(name="test")
	@XmlElement(name = "test")
	private List<String> tests = new ArrayList<String>();
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public List<String> getTests() {
		return tests;
	}
	public void setTests(List<String> tests) {
		this.tests = tests;
	}
	
}
