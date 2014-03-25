package org.w3c.wai.accessdb.services;

import junit.framework.Assert;

import org.junit.Test;
import org.w3c.wai.accessdb.jaxb.LoginData;
import org.w3c.wai.accessdb.om.User;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;

public class AuthenicateServiceTest {
	String pass ="";//FIXME
	@Test
	public void test1() throws ASBPersistenceException {
		LoginData lData = new LoginData("evlachog",pass);
		User user = AuthenicateService.INSTANCE.login(lData);
		boolean isAuthenticatedAsAdmin = AuthenicateService.isAuthenticatedAsAdmin(user);
		System.out.println("isAuthenticatedAsAdmin: " + isAuthenticatedAsAdmin);
		boolean isAuthenticatedAsExpert = AuthenicateService.isAuthenticatedAsExpert(user);
		System.out.println("isAuthenticatedAsExpert: " + isAuthenticatedAsExpert);
		Assert.assertEquals(isAuthenticatedAsExpert, true);
		Assert.assertEquals(isAuthenticatedAsAdmin, true);
	}
	@Test
	public void test2() throws ASBPersistenceException {
		LoginData lData = new LoginData("evlach",pass);
		User user = AuthenicateService.INSTANCE.login(lData);
		boolean isAuthenticatedAsAdmin = AuthenicateService.isAuthenticatedAsAdmin(user);
		System.out.println("isAuthenticatedAsAdmin: " + isAuthenticatedAsAdmin);
		boolean isAuthenticatedAsExpert = AuthenicateService.isAuthenticatedAsExpert(user);
		System.out.println("isAuthenticatedAsExpert: " + isAuthenticatedAsExpert);
		Assert.assertEquals(isAuthenticatedAsExpert, false);
		Assert.assertEquals(isAuthenticatedAsAdmin, true);
	}
	@Test
	public void test3() throws ASBPersistenceException {
		LoginData lData = new LoginData("mkapsi",pass);
		User user = AuthenicateService.INSTANCE.login(lData);
		boolean isAuthenticatedAsAdmin = AuthenicateService.isAuthenticatedAsAdmin(user);
		System.out.println("isAuthenticatedAsAdmin: " + isAuthenticatedAsAdmin);
		boolean isAuthenticatedAsExpert = AuthenicateService.isAuthenticatedAsExpert(user);
		System.out.println("isAuthenticatedAsExpert: " + isAuthenticatedAsExpert);
		Assert.assertEquals(isAuthenticatedAsExpert, true);
		Assert.assertEquals(isAuthenticatedAsAdmin, false);
	}

	// ldapsearch -d 1 -v -x -b 'ou=people,dc=w3,dc=org' -D
			// "uid=evlachog,ou=people,dc=w3,dc=org" 'uid=evlachog' -H
			// ldaps://ldap.w3.org -W '*' memberOf dn:
			// uid=evlachog,ou=people,dc=w3,dc=org

		
}
