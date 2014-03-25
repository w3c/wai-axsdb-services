package org.w3c.wai.accessdb.om;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.w3c.wai.accessdb.om.base.BaseEntity;

/**
 * @author evangelos.vlachogiannis
 * @since 12.09.12
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Entity 
@JsonIgnoreProperties({"techniques"})
public class SuccessCriterio extends BaseEntity  {
	
	private String idRef;
	private String number;
	private String title;
	private String level;
	@XmlTransient
	@JsonIgnore
	@ManyToMany
	private List<Technique> techniques = new ArrayList<Technique>();
	
		
	public SuccessCriterio() {
		super();
	}

	public SuccessCriterio(String number, String level) {
		super();
		this.number = number;
		this.level = level;
	}







	public String getIdRef() {
		return idRef;
	}
	public void setIdRef(String idRef) {
		this.idRef = idRef;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<Technique> getTechniques() {
		return techniques;
	}
	public void setTechniques(List<Technique> techniques) {
		this.techniques = techniques;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	
	
	
}
