package org.w3c.wai.accessdb.om.testunit;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.wai.accessdb.om.base.BaseEntity;

/**
 * Entity implementation class for Entity: Step
 *
 */
@Entity
@XmlRootElement(name="step")
@XmlAccessorType(XmlAccessType.FIELD)
public class Step extends BaseEntity {

	private int orderId=0;
    @Lob
	private String step;

	public Step(String step) {
		this.step = step;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public Step() {
		super();
	}   
	public String getStep() {
		return this.step;
	}

	public void setStep(String step) {
		this.step = step;
	}
   
}
