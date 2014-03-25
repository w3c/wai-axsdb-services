package org.w3c.wai.accessdb.eao;

import java.util.List;

import org.w3c.wai.accessdb.om.User;


/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 12.05.12
 */ 

public class UserEAO extends BaseEAO<User>
{
    
	 
   public User findByUserId(String userId)
   {
	   List<User> u = this.findByNamedQuery("User.findByUserId", userId);
	   if(u.isEmpty())
		   return null;
	   else
		   return u.get(0);
   }
   public User findByMail(String mail)
   {
	   List<User> u = this.findByNamedQuery("User.findByMail", mail);
	   if(u.isEmpty())
		   return null;
	   else
		   return u.get(0);
   }

    /* (non-Javadoc)
     * @see org.w3c.wai.eao.BaseEAO#getEAOClass()
     */
    @Override
    public Class<? extends User> getEntityClass()
    {
        return User.class;
    }
    
  
}
