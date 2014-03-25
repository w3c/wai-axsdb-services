package org.w3c.wai.accessdb.om;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.wai.accessdb.om.base.BaseEntity;

/**
 * @author evangelos.vlachogiannis
 * @since 12.09.12 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Entity  
public class Principle extends BaseEntity  {
	
	private String idRef;
	private String number;
	private String title;   
	@OneToMany
	private List<Guideline> guidelines  = new ArrayList<Guideline>();

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

	public List<Guideline> getGuidelines() {
		return guidelines;
	}

	public void setGuidelines(List<Guideline> guidelines) {
		this.guidelines = guidelines;
	}

	
}
