package org.w3c.wai.accessdb.om.testunit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@XmlType(name = "", propOrder = { "testFile", "resourceFiles"})
@Embeddable
public class Subject implements Serializable{
	@XmlElement(required = true)
	@OneToOne(cascade=CascadeType.ALL)
	private RefFileType testFile;
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@XmlElementWrapper(name="resources")
	private List<RefFileType> resourceFiles;

	public RefFileType getTestFile() {
		return testFile;
	}

	public void setTestFile(RefFileType f) {
		this.testFile = f;
	}

	public List<RefFileType> getResourceFiles() {
		if (resourceFiles == null) {
			resourceFiles = new ArrayList<RefFileType>();
		}
		return this.resourceFiles;
	}

	public void setResourceFiles(List<RefFileType> resourceFiles) {
		this.resourceFiles = resourceFiles;
	}

}
