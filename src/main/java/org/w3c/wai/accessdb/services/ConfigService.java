/**
 * 
 */
package org.w3c.wai.accessdb.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author evangelos.vlachogiannis
 * @since 03.05.12
 * 
 * 
 */

public enum ConfigService {
	INSTANCE;
	Properties config = new Properties();
	Properties configDefault = new Properties();
	public static final String FORM_TESTUNIT_FOLDER = "FORM_TESTUNIT_FOLDER";
	public static final String FORM_TESTUNIT_FORMFIELD_TESTUNITDESCRIPTION = "FORM_TESTUNIT_FORMFIELD_TESTUNITDESCRIPTION";
	public static final String FORM_TESTUNIT_FORMFIELD_CODE = "FORM_TESTUNIT_FORMFIELD_CODE";
	public static final String FORM_TESTUNIT_FORMFIELD_TESTFILE = "FORM_TESTUNIT_FORMFIELD_TESTFILE";
	public static final String TESTING_SESSION_TIMEOUT = "TESTING_SESSION_TIMEOUT";
	public static final String TESTING_SESSION_TASK_INTERVAL = "TESTING_SESSION_TIMEOUT";
	public static final String KEY_GENERATOR_SIZE = "KEY_GENERATOR_SIZE";

	public static final String USER_ROLE_AXSDBCOL_CODE = "USER_ROLE_AXSDBCOL_CODE";
	public static final String USER_ROLE_AXSDBADM_CODE = "USER_ROLE_AXSDBADM_CODE";
	public static final String USER_ROLE_AXSDBW3C_CODE = "USER_ROLE_AXSDBW3C_CODE";
	public static final String LDAP_URL = "LDAP_URL";
	public static final String LDAP_BASE = "LDAP_BASE";

	private ConfigService() {
		this.init();
	}

	private void init() {
		this.configDefault.put(FORM_TESTUNIT_FORMFIELD_TESTUNITDESCRIPTION,
				"TestUnitDescription");
		this.configDefault.put(FORM_TESTUNIT_FORMFIELD_CODE, "testCode");
		this.configDefault.put(FORM_TESTUNIT_FORMFIELD_TESTFILE, "testFile");
		this.configDefault.put(TESTING_SESSION_TIMEOUT, "100000");
		this.configDefault.put(TESTING_SESSION_TASK_INTERVAL, "100000");
		this.configDefault.put(KEY_GENERATOR_SIZE, "6");

		this.configDefault.put(LDAP_URL, "ldaps://ldap.w3.org:636");
		this.configDefault.put(LDAP_BASE, "ou=people,dc=w3,dc=org");
		this.configDefault.put(USER_ROLE_AXSDBCOL_CODE, "69096");
		this.configDefault.put(USER_ROLE_AXSDBADM_CODE, "69097");

		this.configDefault.put("github.username", "69097");
		this.configDefault.put("github.password", "69097");
		this.configDefault.put("github.techniquesBasePath", "69097");
		this.configDefault.put("github.branchNameDefault", "69097");

		String path = "/etc/accessdb/";
		try {
			// load a properties file
			config.load(new FileInputStream(path + "config.properties"));
		} catch (IOException ex) {
			try {
				config.load(new FileInputStream(System.getProperty("user.home")
						+ "/.accessdb/" + "config.properties"));
				// logger.info("success");
			} catch (IOException e) {
				try {
					// logger.info("Trying to find config.properties in " +
					// ConfigService.class.getResource("/").getPath() +
					// "config.properties");
					config.load(new FileInputStream(ConfigService.class
							.getResource("/").getPath() + "config.properties"));
					// logger.info("success");
				} catch (FileNotFoundException e1) {
					// logger.error("Failed to find config.properties");
				} catch (IOException e1) {
					// logger.error("Failed to find config.properties");
					// logger.debug(e1.getLocalizedMessage());
				}
			}
		}
		this.evalVal();
	}

	public void putParamAndReEval(String key, String value) {
		this.config.put(key, value);
		this.evalVal();
	}

	private void evalVal() {
		Enumeration<Object> em = this.config.keys();
		while (em.hasMoreElements()) {
			String str = (String) em.nextElement();
			String val = this.config.getProperty(str,
					this.configDefault.getProperty(str));
			Pattern pt = Pattern.compile("\\{([^}]*)\\}");
			Matcher m = pt.matcher(val);
			if (m.find()) {
				String s = m.group(0);
				String thevar = s.substring(1, s.length() - 1);
				val = m.replaceAll(getConfigParam(thevar));
				this.config.setProperty(str, val);
			}
		}
	}

	public String getConfigParam(String key) {
		return this.config
				.getProperty(key, this.configDefault.getProperty(key));
	}

	public List<String> getConfigParamsList(String key) {
		String param = this.getConfigParam(key);
		List<String> roles = Arrays.asList(param.trim().split(","));
		return roles;
	}

	public String printScript() {

		StringBuffer out = new StringBuffer();
		Enumeration<Object> em = this.config.keys();
		while (em.hasMoreElements()) {
			String str = (String) em.nextElement();
			String val = this.config.getProperty(str,
					this.configDefault.getProperty(str));
			out.append("var " + str + "=" + "\"" + val + "\"" + ";\n");
		}
		return out.toString();
	}

}
