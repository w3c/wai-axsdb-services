package org.w3c.wai.accessdb.authenticate;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.wai.accessdb.om.User;
import org.w3c.wai.accessdb.services.ConfigService;

public class W3LDAPAuthenticator {
	final static Logger logger = LoggerFactory.getLogger(W3LDAPAuthenticator.class);
	private User user = new User();

	public W3LDAPAuthenticator(User user) {
		this.user = user;
	}

	public boolean w3Login(String username, String password) {
		boolean success = false;
		String base = "ou=people,dc=w3,dc=org";
		String ldapURL = "ldaps://ldap.w3.org:636";
		ldapURL = ConfigService.INSTANCE.getConfigParam(ConfigService.LDAP_URL);
		base = ConfigService.INSTANCE.getConfigParam(ConfigService.LDAP_BASE); 
		String dn = "uid=" + username + "," + base;
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapURL);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, dn);
		env.put(Context.SECURITY_CREDENTIALS, password);
		DirContext ctx = null;
		NamingEnumeration results = null;
		try {
			ctx = new InitialDirContext(env);
			logger.info("login success " + username);
			SearchControls controls = new SearchControls();
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String[] returnedAtts = { "mail", "displayName", "memberOf", "cn" };
			controls.setReturningAttributes(returnedAtts);
			results = ctx.search(dn, "(objectclass=*)", controls);
			while (results.hasMore()) {
				SearchResult searchResult = (SearchResult) results.next();
				Attributes attributes = searchResult.getAttributes();
				success = true;
				this.user.setMail(attributes.get("mail").get().toString());
				this.user.setDisplayName(attributes.get("displayName").get()
						.toString());
				this.user.setUserId(username);
				Attribute memberOf = attributes.get("memberOf");
				for (NamingEnumeration ae = memberOf.getAll(); ae.hasMore();) {
					String attr = (String) ae.nextElement();
					LdapName ln = new LdapName(attr);
					for(Rdn rdn : ln.getRdns()) {
					    if(rdn.getType().equalsIgnoreCase("cn") && rdn.getValue()!=null) {
							this.user.getUserRoles().add((String) rdn.getValue());						
					        break;
					    }
					}
				}
			} 
		} catch (AuthenticationException ex) {
			logger.info("Authenitcation failed");
			logger.debug(ex.getLocalizedMessage());
			ex.printStackTrace();
		} catch (NamingException ex) {
			logger.error(ex.getLocalizedMessage());
		}

		return success;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}