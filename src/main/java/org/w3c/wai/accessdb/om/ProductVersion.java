package org.w3c.wai.accessdb.om;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Embeddable
public class ProductVersion implements Comparable<ProductVersion>, Serializable{
	private int major;
	private int minor;
	private int revision;
	private String text;
	public ProductVersion() {

	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	@Override
	public String toString() {
		return this.major + "." + this.minor +"." +this.revision;
	}

	@Override
	public int compareTo(ProductVersion that) {
		if (!this.getText().equalsIgnoreCase(that.getText())){			
		    return -1;			  
		}
		else
		    return 0;
	}
	

}
