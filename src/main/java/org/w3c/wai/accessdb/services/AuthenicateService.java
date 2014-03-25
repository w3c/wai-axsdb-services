package org.w3c.wai.accessdb.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.wai.accessdb.authenticate.W3LDAPAuthenticator;
import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.jaxb.LoginData;
import org.w3c.wai.accessdb.om.User;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;

public enum AuthenicateService {
	INSTANCE;
	private static final Logger logger =  LoggerFactory.getLogger(AuthenicateService.class);
	
	public static boolean isAuthenticatedAsExpert(User user) {
		return isAuthenticatedAsRoleCode(user, ConfigService.INSTANCE.getConfigParam(ConfigService.USER_ROLE_AXSDBCOL_CODE)) || 
				isAuthenticatedAsRoleCode(user, ConfigService.INSTANCE.getConfigParam(ConfigService.USER_ROLE_AXSDBW3C_CODE));
    }
	public static boolean isAuthenticatedAsAdmin(User user) {
		return isAuthenticatedAsRoleCode(user, ConfigService.INSTANCE.getConfigParam(ConfigService.USER_ROLE_AXSDBADM_CODE)) || 
				isAuthenticatedAsRoleCode(user, ConfigService.INSTANCE.getConfigParam(ConfigService.USER_ROLE_AXSDBW3C_CODE));
    }
	public static boolean isAuthenticatedAsRoleCode(User user, String role) {
		if(role!=null)
		{
			if(user.isW3cUser())
			{
				List<String> roles = user.getUserRoles();
				for(String r : roles){
					if(r.equals(role))
						return true;
				}
			}
			else
			{
				return user.getRole().equals(role);
			}
			
		}
        return false;
    }
	
	public User login(LoginData lData) throws ASBPersistenceException 
	{
		User dbUser = EAOManager.INSTANCE.getUserEAO().findByUserId(lData.getUserId());
		if(dbUser!=null && dbUser.isEnabled() && !dbUser.isW3cUser()){
			if(dbUser.getPass().equals(lData.getPass())) {
				dbUser.getUserRoles().add(dbUser.getRole());
				return dbUser;
			}
		}
		else if(dbUser!=null && dbUser.isEnabled() && dbUser.isW3cUser()){
			W3LDAPAuthenticator ldap = new W3LDAPAuthenticator(dbUser);
			boolean success = ldap.w3Login(lData.getUserId(), lData.getPass());
			if(success){
				dbUser = EAOManager.INSTANCE.getUserEAO().persist(ldap.getUser());
			    return dbUser;
			}
			else
				return null;
		}
		else if(dbUser==null) //if not in db.. try ldap and save in db
		{
            logger.info("user not in db");
            W3LDAPAuthenticator ldap = new W3LDAPAuthenticator(new User());
			boolean success = ldap.w3Login(lData.getUserId(), lData.getPass());
			if(success){
				dbUser = ldap.getUser();
				dbUser.setEnabled(true);
				dbUser.setW3cUser(true);
				dbUser = EAOManager.INSTANCE.getUserEAO().persist(dbUser);
	            logger.info("user added in db " + dbUser.getUserId());
				return dbUser;
			}
			else
				return null;			
		}
		return null;
	}
}
