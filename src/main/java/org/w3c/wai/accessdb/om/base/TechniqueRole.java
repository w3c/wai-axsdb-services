package org.w3c.wai.accessdb.om.base;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TechniqueRole")
@XmlEnum
public enum TechniqueRole {
	@XmlEnumValue("sufficient")
	SUFFICIENT("sufficient"), 
	@XmlEnumValue("tech-optional")
	TECHOPTIONAL("tech-optional"), 
	@XmlEnumValue("failures")
	FAILURES("failures");

	private final String value;

	TechniqueRole(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static TechniqueRole fromValue(String v) {
		for (TechniqueRole c : TechniqueRole.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
	@Override
	public String toString() {
		return this.value;
	}
}
