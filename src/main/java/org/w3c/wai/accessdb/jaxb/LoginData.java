package org.w3c.wai.accessdb.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class LoginData {

	private String userId = null;
	private String pass = null;
	private String sessionId = null;

	public LoginData() {
	}

	public LoginData(String userId, String pass) {
		this.userId = userId;
		this.pass = pass;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public boolean isValid() {
		if (this.userId != null && this.getPass() != null
				&& this.getSessionId() != null && this.getPass().length() > 1
				&& this.getUserId().length() > 1 && this.getSessionId().length()>5)
			return true;
		return false;
	}
}
