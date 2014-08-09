package org.w3c.wai.accessdb.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.wai.accessdb.om.product.Product;
import org.w3c.wai.accessdb.utils.JAXBUtils;

@XmlRootElement
public class TestResultViewTableCell 
{
    public static final String TYPE_HEADER = "th";
    public static final String TYPE_DATA = "td";
    private String type = "undefined";
    private Product product = null; 
    private String noOfPass = "undefined";
    private String noOfAll = "undefined";
    private TestResultData resData = null;
    
    public TestResultData getResData() {
		return resData;
	}
	public void setResData(TestResultData resData) {
		this.resData = resData;
	}
	public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public Product getProduct()
    {
        return product;
    }
    public void setProduct(Product product)
    {
        this.product = product;
    }
    public String getNoOfPass()
    {
        return noOfPass;
    }
    public void setNoOfPass(String noOfPass)
    {
        this.noOfPass = noOfPass;
    }
    public String getNoOfAll()
    {
        return noOfAll;
    }
    public void setNoOfAll(String noOfAll)
    {
        this.noOfAll = noOfAll;
    }   
    @Override
    public String toString()
    {
        return JAXBUtils.objectToJSONString(this);
    }
}
