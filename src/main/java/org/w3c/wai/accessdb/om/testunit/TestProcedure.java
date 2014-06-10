package org.w3c.wai.accessdb.om.testunit;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="step" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="yesNoQuestion" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="expectedResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "step",
    "yesNoQuestion",
    "expectedResult"
})
@Embeddable
public class TestProcedure implements Serializable{

	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)	
	@OrderBy("id ASC")
	@XmlElementWrapper(name="steps")
    private Set<Step> step = new HashSet<Step>();
	@Column(columnDefinition = "LONGBLOB") 
	private String yesNoQuestion;
	private boolean expectedResult;

    /**
     * Gets the value of the step property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the step property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStep().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */


    /**
     * Gets the value of the yesNoQuestion property.
     * 
     */
    public String isYesNoQuestion() {
        return yesNoQuestion;
    }

    public Set<Step> getStep() {
		return step;
	}

	public void setStep(Set<Step> step) {
		this.step = step;
	}

	/**
     * Sets the value of the yesNoQuestion property.
     * 
     */
    public void setYesNoQuestion(String value) {
        this.yesNoQuestion = value;
    }

    /**
     * Gets the value of the expectedResult property.
     * 
     */
    public boolean isExpectedResult() {
        return expectedResult;
    }

    /**
     * Sets the value of the expectedResult property.
     * 
     */
    public void setExpectedResult(boolean value) {
        this.expectedResult = value;
    }

	public String getYesNoQuestion() {
		return yesNoQuestion;
	}


}


