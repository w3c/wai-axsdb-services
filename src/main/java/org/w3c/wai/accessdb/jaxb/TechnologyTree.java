package org.w3c.wai.accessdb.jaxb;

import org.w3c.wai.accessdb.utils.JAXBUtils;
/**
 * @author evangelos.vlachogiannis
 * @since 18.06.12 
 */
public class TechnologyTree {
	private TreeNodeData rootNode = new TreeNodeData();
	int nodeNum=0;
	public void addCombination(TechnologyCombination comb) {
		// for each level run all comb
		TreeNodeData atNameNode = this.createATNameNode(comb);
		TreeNodeData atVersionNode = this.createATVersionNode(comb);
		TreeNodeData uaNameNode = this.createUANameNode(comb);
		TreeNodeData uaVersionNode = this.createUAVersionNode(comb);
		TreeNodeData osNameNode = this.createOSNameNode(comb);
		TreeNodeData osVersionNode = this.createOSVersionNode(comb);
		this.rootNode
			.addUnique(atNameNode)
			.addUnique(atVersionNode)
			.addUnique(uaNameNode)
			.addUnique(uaVersionNode)
			.addUnique(osNameNode)
			.addUnique(osVersionNode);
	}

	public TreeNodeData createATNameNode(TechnologyCombination comb) {
		TreeNodeData node = new TreeNodeData();
		node.setId(String.valueOf(nodeNum += 1));
		node.setValue(comb.getAtName());
		node.setLabel(comb.getAtName());
		node.setType("atNameNode");
		return node;
	}

	public TreeNodeData createATVersionNode(TechnologyCombination comb) {
		TreeNodeData node = new TreeNodeData();
		node.setId(String.valueOf(nodeNum += 1));
		node.setValue(comb.getAtVersion());
		node.setLabel(comb.getAtVersion());
		node.setType("atVersionNode");
		return node;
	}

	public TreeNodeData createUANameNode(TechnologyCombination comb) {
		TreeNodeData node = new TreeNodeData();
		node.setId(String.valueOf(nodeNum += 1));
		node.setValue(comb.getUaName());
		node.setLabel(comb.getUaName());
		node.setType("uaNameNode");
		return node;
	}

	public TreeNodeData createUAVersionNode(TechnologyCombination comb) {
		TreeNodeData node = new TreeNodeData();
		node.setId(String.valueOf(nodeNum += 1));
		node.setValue(comb.getUaVersion());
		node.setLabel(comb.getUaVersion());
		node.setType("uaVersionNode");
		return node;
	}

	public TreeNodeData createOSNameNode(TechnologyCombination comb) {
		TreeNodeData node = new TreeNodeData();
		node.setId(String.valueOf(nodeNum += 1));
		node.setValue(comb.getOsName());
		node.setLabel(comb.getOsName());
		node.setType("osNameNode");
		return node;
	}

	public TreeNodeData createOSVersionNode(TechnologyCombination comb) {
		TreeNodeData node = new TreeNodeData();
		node.setId(String.valueOf(nodeNum += 1));
		node.setValue(comb.getOsVersion());
		node.setLabel(comb.getOsVersion());
		node.setType("osVersionNode");
		return node;
	}

	public TreeNodeData getRootNode() {
		return rootNode;
	}
	@Override
	public String toString() {
		return JAXBUtils.objectToJSONString(this.rootNode);
	}
}
