package org.w3c.wai.accessdb.om.product;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 25.01.12
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity 
public class Plugin extends Product<Plugin>
{
	public Plugin(String name, String ver) {
		super(name, ver);
	}

	public Plugin() {
		super();
	}
}