package org.w3c.wai.accessdb.om;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.wai.accessdb.om.base.BaseEntity;

/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 25.01.12
 */

@XmlRootElement
@Entity
public class UserTestingProfile extends BaseEntity {
	 
	private String profileName;
	@Embedded
	private TestingProfile profile = new TestingProfile();
	
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}	

	public String toText()
	{
		return this.profileName;
	}
	public TestingProfile getProfile() {
		return profile;
	}
	public void setProfile(TestingProfile profile) {
		this.profile = profile;
	}


}
