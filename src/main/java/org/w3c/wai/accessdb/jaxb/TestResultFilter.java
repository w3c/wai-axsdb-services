package org.w3c.wai.accessdb.jaxb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.wai.accessdb.om.base.BaseEntity;

@XmlRootElement(name="testRusultsFilter")
@XmlAccessorType(XmlAccessType.FIELD)

public class TestResultFilter extends BaseEntity{
	private Date lastModified = new Date();
	private String userName; 
	private String criteriosLevel;
	private List<String> criterios = new ArrayList<String>();	
	private List<String> technologies = new ArrayList<String>();	
	private List<SimpleProduct> ats = new ArrayList<SimpleProduct>();
	private List<SimpleProduct> uas = new ArrayList<SimpleProduct>();
	private List<SimpleProduct> oss = new ArrayList<SimpleProduct>();
	private List<String> techniques = new ArrayList<String>();	
	private List<String> tests = new ArrayList<String>();	
	private List<String> statusList = new ArrayList<String>();	
	
	
	public List<String> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}
	public List<String> getTechniques() {
		return techniques;
	}
	public void setTechniques(List<String> techniques) {
		this.techniques = techniques;
	}
	public List<String> getTests() {
		return tests;
	}
	public void setTests(List<String> tests) {
		this.tests = tests;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCriteriosLevel() {
		return criteriosLevel;
	}
	public void setCriteriosLevel(String criteriosLevel) {
		this.criteriosLevel = criteriosLevel;
	}
	public List<String> getCriterios() {
		return criterios;
	}
	public void setCriterios(List<String> criterios) {
		this.criterios = criterios;
	}
	public List<String> getTechnologies() {
		return technologies;
	}
	public void setTechnologies(List<String> technologies) {
		this.technologies = technologies;
	}
	public List<SimpleProduct> getAts() {
		return ats;
	}
	public void setAts(List<SimpleProduct> ats) {
		this.ats = ats;
	}
	public List<SimpleProduct> getUas() {
		return uas;
	}
	public void setUas(List<SimpleProduct> uas) {
		this.uas = uas;
	}
	public List<SimpleProduct> getOss() {
		return oss;
	}
	public void setOss(List<SimpleProduct> oss) {
		this.oss = oss;
	}	
}
