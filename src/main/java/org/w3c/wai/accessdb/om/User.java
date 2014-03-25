package org.w3c.wai.accessdb.om;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.ws.rs.Encoded;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.wai.accessdb.om.base.BaseEntity;

/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 25.01.12
 */

@XmlRootElement
@Entity
public class User extends BaseEntity {

	@OneToMany(cascade=CascadeType.ALL )
	private List<UserTestingProfile> userTestingProfiles = new ArrayList<UserTestingProfile>();
	@Basic
	private String userId;
	
	@Basic
	private String displayName;
	//only for non ldap users
	@Encoded
	private String pass;
	//only for non ldap users
	private String role;
	//coming from ldap for the session
	@Transient
	private List<String> userRoles = new ArrayList<String>(); 
	
	@Basic
	private String mail;

	private boolean w3cUser = true;
	
	public boolean isW3cUser() {
		return w3cUser;
	}

	public void setW3cUser(boolean w3cUser) {
		this.w3cUser = w3cUser;
	}

	private boolean enabled=true;
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public List<UserTestingProfile> getUserTestingProfiles() {
		return userTestingProfiles;
	}

	public void setUserTestingProfiles(List<UserTestingProfile> userTestingProfiles) {
		this.userTestingProfiles = userTestingProfiles;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public List<String> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<String> userRoles) {
		this.userRoles = userRoles;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return this.userId + this.displayName + this.mail;
	}
	
}