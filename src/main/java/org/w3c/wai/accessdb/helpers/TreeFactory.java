package org.w3c.wai.accessdb.helpers;

import java.util.List;

import org.w3c.wai.accessdb.jaxb.SimpleProduct;
import org.w3c.wai.accessdb.jaxb.TreeNodeData;

public class TreeFactory {
	public static TreeNodeData buildProductNode(String productType, List<SimpleProduct> products){
		TreeNodeData rootNode = new TreeNodeData();
		rootNode.setType("ROOT");
		for (SimpleProduct p : products) {	
		    if(p.getName().trim().length()>0)
		    {
		        p.setType(productType);
	            rootNode
	                .addUnique(p.toNameTreeNodeDate())
	                .addUnique(p.toVersionTreeNodeDate());
		    }
		}		
		return rootNode;
	}
	
	
}
