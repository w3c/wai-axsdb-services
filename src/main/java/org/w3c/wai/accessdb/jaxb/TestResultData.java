package org.w3c.wai.accessdb.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.wai.accessdb.utils.JAXBUtils;

@XmlRootElement
public class TestResultData implements Comparable<TestResultData>
{ 
    private SimpleProduct uaProduct = null; 
    private SimpleProduct atProduct = null; 
    private String noOfPass = "undefined";
    private String noOfAll = "undefined";

    public SimpleProduct getUaProduct() {
		return uaProduct;
	}

	public void setUaProduct(SimpleProduct uaProduct) {
		this.uaProduct = uaProduct;
	}

	public SimpleProduct getAtProduct() {
		return atProduct;
	}

	public void setAtProduct(SimpleProduct atProduct) {
		this.atProduct = atProduct;
	}

	public String getNoOfPass() {
		return noOfPass;
	}

	public void setNoOfPass(String noOfPass) {
		this.noOfPass = noOfPass;
	}

	public String getNoOfAll() {
		return noOfAll;
	}

	public void setNoOfAll(String noOfAll) {
		this.noOfAll = noOfAll;
	}

	@Override
    public String toString()
    {
        return JAXBUtils.objectToJSONString(this);
    }

	@Override
	public int compareTo(TestResultData d) {
		String s1 = d.getUaProduct().getName() + " " + d.getUaProduct().getVersion();
		String s2 = this.getUaProduct().getName() + " " + this.getUaProduct().getVersion();
		return s1.compareTo(s1);
	}
}
