package org.w3c.wai.accessdb.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.wai.accessdb.om.product.Product;

@XmlRootElement
public class SimpleProduct { 
	private int id;
	private String type;
	private String name;
	private String version;
	public SimpleProduct() {

	}
	public SimpleProduct(String type, String name, String version) {
		this.setName(name);
		this.setType(type);
		this.setVersion(version);
	}
	public SimpleProduct(String name, String version) {
		this.setName(name);
		this.setVersion(version);
	}
    public SimpleProduct(Product p) {
        this.setName(p.getName());
        this.setVersion(p.getVersion().getText());
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public TreeNodeData toNameTreeNodeDate() {
		TreeNodeData node = new TreeNodeData();
		node.setId(String.valueOf(this.getId()));
		node.setValue(this.getName());
		node.setLabel(this.getName());
		node.setType(this.getType());
		return node;
	}
	public TreeNodeData toVersionTreeNodeDate() {
		TreeNodeData node = new TreeNodeData();
		node.setId(String.valueOf(this.getId()));
		node.setValue(this.getVersion());
		node.setLabel(this.getVersion());
		node.setType(this.getType()+"_version");
		return node;
	}
}
