package org.w3c.wai.accessdb.om;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.annotations.Cascade;
import org.w3c.wai.accessdb.om.base.BaseEntity;
import org.w3c.wai.accessdb.utils.DateAdapter;

/**
 * Entity implementation class for Entity: TestResultSet
 *
 */
@Entity
@XmlRootElement 
public class TestResultsBunch extends BaseEntity {

	private String optionalName = null;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REFRESH})
	private List<TestResult> results  = new ArrayList<TestResult>();
	@XmlJavaTypeAdapter(DateAdapter.class)
    @XmlSchemaType(name = "date")
    private Date date;
	@OneToOne
	private User user;
	public String getOptionalName() {
		return optionalName;
	}
	public void setOptionalName(String optionalName) {
		this.optionalName = optionalName;
	}
	public List<TestResult> getResults() {
		return results;
	}
	public void setResults(List<TestResult> results) {
		this.results = results;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
