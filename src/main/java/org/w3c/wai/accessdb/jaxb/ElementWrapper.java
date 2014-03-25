package org.w3c.wai.accessdb.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "entities")
//@XmlSeeAlso({RequirementInfo.class })	
/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 12.05.12
 */
public class ElementWrapper<T> {
	
	@XmlElement(name = "entity") //TODO: remove
	private List<T> entities = new ArrayList<T>();
 
	public ElementWrapper(List<T> entities) {
		this.entities = entities;
	}

	public List<T> getList() {
		return entities;
	}

	public void setList(List<T> entities) {
		this.entities = entities;
	}

	public ElementWrapper() {
	}
	
}
