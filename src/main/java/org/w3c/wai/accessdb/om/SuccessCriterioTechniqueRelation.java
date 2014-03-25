package org.w3c.wai.accessdb.om;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.wai.accessdb.om.base.BaseEntity;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Entity
public class SuccessCriterioTechniqueRelation extends BaseEntity{

	@OneToOne(cascade=CascadeType.MERGE)
	private SuccessCriterio successCriterio;	
	@OneToOne(cascade=CascadeType.MERGE)
	private Technique technique;
	private String techniqueRole;
	private String techniqueGroup;
	private String meta;

	public SuccessCriterioTechniqueRelation() {
	}   
	public SuccessCriterio getSuccessCriterio() {
		return successCriterio;
	}
	public void setSuccessCriterio(SuccessCriterio successCriterio) {
		this.successCriterio = successCriterio;
	}
	public Technique getTechnique() {
		return technique;
	}
	public void setTechnique(Technique technique) {
		this.technique = technique;
	}
	public String getTechniqueRole() {
		return techniqueRole;
	}
	public void setTechniqueRole(String techniqueRole) {
		this.techniqueRole = techniqueRole;
	}
	public String getTechniqueGroup() {
		return techniqueGroup;
	}
	public void setTechniqueGroup(String techniqueGroup) {
		this.techniqueGroup = techniqueGroup;
	}
	@Override
	public String toString() {
		return "Tech: " + this.technique.getNameId() + "Sc: " + this.getSuccessCriterio().getNumber() + "Role: " + this.getTechniqueRole();
	}
	public String getMeta() {
		return meta;
	}
	public void setMeta(String meta) {
		this.meta = meta;
	}   
}
