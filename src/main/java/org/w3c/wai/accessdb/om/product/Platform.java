package org.w3c.wai.accessdb.om.product;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity 
public class Platform extends Product<Platform> {
	private String architecture;
	
	public Platform() {
		super();
	}

	public Platform(Product p) {
		super(p.getName(), p.getVersion().getText());
	}
	public Platform(String name, String ver, String arc) {
		super(name, ver);
		this.setArchitecture(arc);
	}
	public Platform(String name, String ver) {
		super(name, ver);
	}
	public String getArchitecture() {
		return architecture;
	}
	public void setArchitecture(String architecture) {
		this.architecture = architecture;
	}
	@Override
	public String toString() {
		return this.getName() + "/v." + this.getVersion() + "/" +this.getArchitecture();
	}
	


}