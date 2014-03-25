package org.w3c.wai.accessdb.om;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.wai.accessdb.om.base.BaseEntity;

/**
 * Entity implementation class for Entity: UserRole
 *
 */
@XmlRootElement
//@Entity
public class UserRole  {	//extends BaseEntity
	private String name = null;
	private String code = null;
	private String description = null;
	public UserRole() {
	}
	public UserRole(String name, String code) {
		this.name = name;
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
   
}
