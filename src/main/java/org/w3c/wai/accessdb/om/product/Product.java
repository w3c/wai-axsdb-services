package org.w3c.wai.accessdb.om.product;

import javax.persistence.Basic;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.wai.accessdb.helpers.VersionHelper;
import org.w3c.wai.accessdb.om.ProductVersion;
import org.w3c.wai.accessdb.om.base.BaseEntity;
/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @param <T>
 * @since 25.01.12
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Product<T extends Product<?>> extends BaseEntity{
	@Basic
	private String name;
	@Basic
	private String vendor;
	@Embedded
	private ProductVersion version = new ProductVersion();
		
	public Product() {
		super();
	}
	public Product(String name, String ver) {
		this.setName(name);
		this.setVersion(VersionHelper.parseVersion(ver));
	}
	public void setName(String param) {
		this.name = param;
	}

	public String getName() {
		return name;
	}

	public void setVendor(String param) {
		this.vendor = param;
	}
 
	public String getVendor() {
		return vendor;
	}

	public ProductVersion getVersion() {
		return version;
	}
	public void setVersion(ProductVersion version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return this.getName() + "(Ver, " + this.getVersion() + ")";
	}
	@Override
    public boolean equals(Object o)
    {
        Product that = (Product) o;
        if (this.getName() == that.getName()
                && this.getVersion().getText() == that.getVersion().getText()
                && this.getVendor() == that.getVendor())
            return true;
        else
            return false;
    }
	
    
}