package org.w3c.wai.accessdb.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class RatingSimple {
	private String ratingValue;
	private String comment;
	private String experience;
	private String ratedId;
	private String ratedType;
	public String getRatingValue() {
		return ratingValue;
	}
	public void setRatingValue(String ratingValue) {
		this.ratingValue = ratingValue;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getRatedId() {
		return ratedId;
	}
	public void setRatedId(String ratedId) {
		this.ratedId = ratedId;
	}
	public String getRatedType() {
		return ratedType;
	}
	public void setRatedType(String ratedType) {
		this.ratedType = ratedType;
	}
	
}
