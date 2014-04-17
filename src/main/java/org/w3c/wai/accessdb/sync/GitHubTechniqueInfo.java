package org.w3c.wai.accessdb.sync;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.wai.accessdb.om.WebTechnology;

@XmlRootElement
public class GitHubTechniqueInfo {
	private String technique = null;
	private WebTechnology webTechnology = null;
	private String sha = null;
	private Date date = null;
	private String diffUrl = null;
	private String url = null;

	public String getTechnique() {
		return technique;
	}

	public void setTechnique(String technique) {
		this.technique = technique;
	}

	public WebTechnology getWebTechnology() {
		return webTechnology;
	}

	public void setWebTechnology(WebTechnology webTechnology) {
		this.webTechnology = webTechnology;
	}

	public String getSha() {
		return sha;
	}

	public void setSha(String sha) {
		this.sha = sha;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDiffUrl() {
		return diffUrl;
	}

	public void setDiffUrl(String diffUrl) {
		this.diffUrl = diffUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return this.technique + " " + this.webTechnology.getNameId() + " "
				+ this.getUrl() + " " + this.getSha();
	}

}
