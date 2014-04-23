package org.w3c.wai.accessdb.utils;

import javax.ws.rs.core.Response;

public class AuthenticationException extends Exception
{
	private Response.Status errorStatus = null;

    public AuthenticationException(Response.Status t)
    {
    	this.errorStatus=t;
    }
    
	public Response.Status getErrorStatus() {
		return errorStatus;
	}

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

}
