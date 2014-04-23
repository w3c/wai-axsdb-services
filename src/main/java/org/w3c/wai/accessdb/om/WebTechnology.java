package org.w3c.wai.accessdb.om;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.wai.accessdb.om.base.BaseEntity;


/**
 * @author evangelos.vlachogiannis
 * @since 14.03.12
 */
@XmlRootElement
@Entity
public class WebTechnology extends BaseEntity {
	@Column(unique=true)
	private String nameId;
	@Basic
	private String reqSourceDoc;
	@Basic
	private String reqRefDoc;
	@Basic
	private String techniquesRefDoc;
	@Basic
    private String name;
	@Basic
    private String path;	   

	public WebTechnology() {
	}
	public WebTechnology(String nameId, String reqSourceDoc, String reqRefDoc) {
		this.nameId = nameId;
		this.reqSourceDoc = reqSourceDoc;
		this.reqRefDoc = reqRefDoc;
		this.path = nameId;
	}
		
	public String getNameId() {
		return nameId;
	}
	public void setNameId(String nameId) {
		this.nameId = nameId;
	}
	public String getReqSourceDoc() {
		return reqSourceDoc;
	}
	public void setReqSourceDoc(String reqSourceDoc) {
		this.reqSourceDoc = reqSourceDoc;
	}
	public String getReqRefDoc() {
		return reqRefDoc;
	}
	public void setReqRefDoc(String reqRefDoc) {
		this.reqRefDoc = reqRefDoc;
	}
	public String getTechniquesRefDoc() {
		return techniquesRefDoc;
	}
	public void setTechniquesRefDoc(String techniquesRefDoc) {
		this.techniquesRefDoc = techniquesRefDoc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	

}