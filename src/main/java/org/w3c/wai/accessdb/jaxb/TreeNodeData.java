package org.w3c.wai.accessdb.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.w3c.wai.accessdb.utils.JAXBUtils;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
/**
 * @author evlach
 *
 */
@JsonIgnoreProperties({"id"})
public class TreeNodeData implements Comparable<TreeNodeData> {
	private String label = "ROOT";
	@XmlTransient
	@JsonIgnore
	private String id = "-1";
	public List<TreeNodeData> children = new ArrayList<TreeNodeData>();
	private String value;
	private boolean selected = false;
	private boolean disabled = false;
	private boolean subselector = true;
	private boolean selectable = true;
	private boolean collapsed = true;
	private String type = "";

	private String description;
	private int noOfChildren = 0;

	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isSubselector() {
		return subselector;
	}

	public void setSubselector(boolean subselector) {
		this.subselector = subselector;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNoOfChildren() {
		return noOfChildren;
	}

	public void setNoOfChildren(int noOfChildren) {
		this.noOfChildren = noOfChildren;
	}

	public List<TreeNodeData> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNodeData> children) {
		this.children = children;
	}

	public void addChild(TreeNodeData node) {
		this.getChildren().add(node);
	}

	public TreeNodeData addUnique(TreeNodeData node) {
		TreeNodeData existingNode = this.getNode(node);
		if (existingNode == null) {
			this.getChildren().add(node);
			existingNode = node;
		}
		return existingNode;
	}

	public boolean hasNode(TreeNodeData newNode) {
		if (this.getNode(newNode) != null)
			return true;
		return false;
	}

	public TreeNodeData getNode(TreeNodeData newNode) {
		List<TreeNodeData> nodes = this.getChildren();
		for (TreeNodeData treeNodeData : nodes) {
			if (treeNodeData.equals(newNode)) {
				return treeNodeData;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return JAXBUtils.objectToJSONString(this);
	}

	@Override
	public int compareTo(TreeNodeData other) {
		if (other == null)
			return -1;
		return this.getValue().compareTo(other.getValue());
	}

	@Override
	public boolean equals(Object obj) {
		if (this.compareTo((TreeNodeData) obj) == 0)
			return true;
		else
			return false;
	}

	public boolean isCollapsed() {
		return collapsed;
	}

	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}
}
