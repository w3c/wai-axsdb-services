package org.w3c.wai.accessdb.om;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.wai.accessdb.om.base.BaseEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Entity
public class Rating extends BaseEntity {

	private int ratingValue;
	private String comment;
	private int experience;
	private Long ratedId;
	private String ratedType;
	public int getRatingValue() {
		return ratingValue;
	}
	public void setRatingValue(int ratingValue) {
		this.ratingValue = ratingValue;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public Long getRatedId() {
		return ratedId;
	}
	public void setRatedId(Long ratedId) {
		this.ratedId = ratedId;
	}
	public String getRatedType() {
		return ratedType;
	}
	public void setRatedType(String ratedType) {
		this.ratedType = ratedType;
	}


}