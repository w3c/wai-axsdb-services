package org.w3c.wai.accessdb.om;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.w3c.wai.accessdb.om.base.BaseEntity;
import org.w3c.wai.accessdb.utils.WebTechnologyAdapter;
/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 12.05.12
 */
@XmlAccessorType(XmlAccessType.FIELD)

@XmlRootElement(name = "technique")
@Entity
@JsonIgnoreProperties({"webTechnology"})
public class Technique extends BaseEntity{
	@Column(unique=true)
	private String nameId;
	private String specRef;
	@Column(columnDefinition="text")
	private String title;	
	@OneToOne
    @XmlJavaTypeAdapter(WebTechnologyAdapter.class)
	private WebTechnology webTechnology;
	private int status = 1;
	private Date lastModified = null;
	private String sha = null;
	public Technique() {
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}	
	
	public Technique(String nameId) {
		this.nameId = nameId;
	}

	public String getSpecRef() {
		return specRef;
	}

	public void setSpecRef(String specRef) {
		this.specRef = specRef;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}	
	
	public String getNameId() {
		return nameId;
	}
	public void setNameId(String nameId) {
		this.nameId = nameId;
	}
	@Override
	public String toString() {
		return this.specRef + "#" + this.getNameId();
	}
	public WebTechnology getWebTechnology() {
		return webTechnology;
	}
	public void setWebTechnology(WebTechnology webTechnology) {
		this.webTechnology = webTechnology;
	}

	public String getSha() {
		return sha;
	}

	public void setSha(String sha) {
		this.sha = sha;
	}

	
}
