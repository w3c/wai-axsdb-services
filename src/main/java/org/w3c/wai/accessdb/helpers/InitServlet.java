package org.w3c.wai.accessdb.helpers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.w3c.wai.accessdb.services.DBInitService;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;
public class InitServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 800058150221875367L;

	@Override
	public void init() throws ServletException {
		try {
			DBInitService.INSTANCE.initAll();
		} catch (ASBPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


} 
