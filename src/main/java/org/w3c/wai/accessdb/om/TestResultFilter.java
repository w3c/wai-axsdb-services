package org.w3c.wai.accessdb.om;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.w3c.wai.accessdb.om.base.BaseEntity;
import org.w3c.wai.accessdb.om.product.AssistiveTechnology;
import org.w3c.wai.accessdb.om.product.Platform;
import org.w3c.wai.accessdb.om.product.UAgent;
import org.w3c.wai.accessdb.utils.DateAdapter;

@XmlRootElement(name="testRusultsFilter")
@XmlAccessorType(XmlAccessType.FIELD)
//@Entity
//TODO: add to DB
public class TestResultFilter extends BaseEntity{
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date lastModified;
	
	//@XmlJavaTypeAdapter(EntityIdAdapter.class)	
	@XmlElement(name = "user")
	@OneToOne
	private User user; 
	
	private String criteriosLevel;
	
	//@XmlJavaTypeAdapter(EntityIdAdapter.class)	
	@XmlElementWrapper(name="criterios")
	@XmlElement(name = "criterio")
	@OneToMany
	List<SuccessCriterio> criterios = new ArrayList<SuccessCriterio>();
	
	//@XmlJavaTypeAdapter(EntityIdAdapter.class)	
	@XmlElementWrapper(name="technologies")
	@XmlElement(name = "technology")
	@OneToMany
	List<WebTechnology> technologies = new ArrayList<WebTechnology>();
	
	//@XmlJavaTypeAdapter(EntityIdAdapter.class)	
	@XmlElementWrapper(name="techniques")
	@XmlElement(name = "technique")
	@OneToMany
	List<Technique> techniques = new ArrayList<Technique>();
	
	//@XmlJavaTypeAdapter(EntityIdAdapter.class)	
	@XmlElementWrapper(name="ats")
	@XmlElement(name = "at")
	@OneToMany
	List<AssistiveTechnology> ats = new ArrayList<AssistiveTechnology>();
	
	//@XmlJavaTypeAdapter(EntityIdAdapter.class)	
	@XmlElementWrapper(name="uas")
	@XmlElement(name = "ua")
	@OneToMany
	List<UAgent> uas = new ArrayList<UAgent>();
	
	//@XmlJavaTypeAdapter(EntityIdAdapter.class)	
	@XmlElementWrapper(name="oss")
	@XmlElement(name = "os")
	@OneToMany
	List<Platform> oss = new ArrayList<Platform>();
	
	private byte minTests = 1;
	
	private byte minPercentage = 0;

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCriteriosLevel() {
		return criteriosLevel;
	}

	public void setCriteriosLevel(String criteriosLevel) {
		this.criteriosLevel = criteriosLevel;
	}

	public List<SuccessCriterio> getCriterios() {
		return criterios;
	}

	public void setCriterios(List<SuccessCriterio> criterios) {
		this.criterios = criterios;
	}

	public List<WebTechnology> getTechnologies() {
		return technologies;
	}

	public void setTechnologies(List<WebTechnology> technologies) {
		this.technologies = technologies;
	}

	public List<Technique> getTechniques() {
		return techniques;
	}

	public void setTechniques(List<Technique> techniques) {
		this.techniques = techniques;
	}

	public List<AssistiveTechnology> getAts() {
		return ats;
	}

	public void setAts(List<AssistiveTechnology> ats) {
		this.ats = ats;
	}

	public List<UAgent> getUas() {
		return uas;
	}

	public void setUas(List<UAgent> uas) {
		this.uas = uas;
	}

	public List<Platform> getOss() {
		return oss;
	}

	public void setOss(List<Platform> oss) {
		this.oss = oss;
	}

	public byte getMinTests() {
		return minTests;
	}

	public void setMinTests(byte minTests) {
		this.minTests = minTests;
	}

	public byte getMinPercentage() {
		return minPercentage;
	}

	public void setMinPercentage(byte minPercentage) {
		this.minPercentage = minPercentage;
	}
	
	
}
