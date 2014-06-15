package org.w3c.wai.accessdb.om;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.wai.accessdb.om.product.AssistiveTechnology;
import org.w3c.wai.accessdb.om.product.Platform;
import org.w3c.wai.accessdb.om.product.Plugin;
import org.w3c.wai.accessdb.om.product.UAgent;

/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 25.01.12
 */

@XmlRootElement
@Embeddable
public class TestingProfile  implements Comparable<TestingProfile>, Serializable {
	
	private String profileComment;
	@ManyToOne(cascade=CascadeType.ALL)
	private AssistiveTechnology assistiveTechnology;
	@ManyToOne(cascade=CascadeType.ALL)
	private Platform platform =new Platform();
	@ManyToOne(cascade=CascadeType.ALL)
	private UAgent userAgent= new UAgent();
	@ManyToOne(cascade=CascadeType.ALL)
	private Plugin plugin = new Plugin();

	
	public TestingProfile() {
		
	}
	
	public String getProfileComment() {
		return profileComment;
	}
	public void setProfileComment(String profileComment) {
		this.profileComment = profileComment;
	}
	public TestingProfile(TestingProfile o) {
		this.assistiveTechnology = o.getAssistiveTechnology();
		this.platform = o.getPlatform();
		this.plugin = o.getPlugin();
		this.userAgent = o.getUserAgent();
	}

	public AssistiveTechnology getAssistiveTechnology() {
		if(this.assistiveTechnology==null)
			this.assistiveTechnology = new AssistiveTechnology();
		return assistiveTechnology;
	}


	public void setAssistiveTechnology(AssistiveTechnology assistiveTechnology) {
		this.assistiveTechnology = assistiveTechnology;
	}


	public Platform getPlatform() {
		if(this.platform==null)
			this.platform = new Platform();
		return platform;
	}


	public void setPlatform(Platform platform) {
		
		this.platform = platform;
	}


	public UAgent getUserAgent() {
		if(this.userAgent==null)
			this.userAgent = new UAgent();
		return userAgent;
	}


	public void setUserAgent(UAgent userAgent) {
		this.userAgent = userAgent;
	}


	public Plugin getPlugin() {
		if(this.plugin==null)
			this.plugin = new Plugin();
		return this.plugin;
	}


	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}
	@Override
	public int compareTo(TestingProfile p) {
		if(this.assistiveTechnology.equals(p.assistiveTechnology) &&
				this.platform.equals(p.platform) &&	
				this.plugin.equals(p.plugin) &&	
				this.userAgent.equals(p.userAgent) 
				)
			return 0;
		else
			return -1;
	}
	

}
