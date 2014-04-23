package org.w3c.wai.accessdb.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.om.User;
import org.w3c.wai.accessdb.sync.TechniquesSpecParser;
import org.w3c.wai.accessdb.sync.WCAG2SpecParser;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;
public enum DBInitService {
	INSTANCE;
	private static final Logger logger =  LoggerFactory.getLogger(DBInitService.class);  
	final static String XML_URL_BASE = "https://raw.github.com/w3c/wcag/master/wcag20/sources/";
	public static void main(String[] args) throws ASBPersistenceException
	{
		DBInitService.INSTANCE.initAll();
	}
	public void initAll() throws ASBPersistenceException
	{
		DBInitService.INSTANCE.initUsers();
		DBInitService.INSTANCE.initRequirements();
		logger.info("init all DB done");
		
	}
	public void initUsers() throws ASBPersistenceException{
		User anon = new User();
		anon.setRole("anon");
		anon.setDisplayName("Anonymous");
		anon.setUserId("anon");
		EAOManager.INSTANCE.getUserEAO().persist(anon);
		User admin = new User();
		admin.setRole("69097");
		admin.setW3cUser(false);
		admin.setDisplayName("admin");
		admin.setUserId("admin");
		admin.setPass("a11y!!");
		EAOManager.INSTANCE.getUserEAO().persist(admin);
	}
	public void initRequirements() throws ASBPersistenceException{
		if(EAOManager.INSTANCE.getCriterioEAO().findAll().size()<1){
			try {
				WCAG2SpecParser.parse(XML_URL_BASE + "wcag2-src.xml");
				//TechniquesSpecParser.parse(XML_URL_BASE + "html-tech-src.xml");
				//TechniquesSpecParser.parse(XML_URL_BASE + "css-tech.xml");
				//TechniquesSpecParser.parse(XML_URL_BASE + "ARIA-tech-src.xml");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
}
