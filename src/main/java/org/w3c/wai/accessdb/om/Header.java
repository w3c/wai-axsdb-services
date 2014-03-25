package org.w3c.wai.accessdb.om;

import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.wai.accessdb.om.base.BaseEntity;

@XmlRootElement
//@Entity
public class Header extends BaseEntity {
	private String name;
	private String value;

	public Header() {

	}

	public Header(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
