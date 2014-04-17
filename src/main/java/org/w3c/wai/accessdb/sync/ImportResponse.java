package org.w3c.wai.accessdb.sync;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * @author Vlachogiannis
 *
 * @param <T>
 */
@XmlRootElement
public class ImportResponse<T>{
	public static int ONLY_IN_WCAG = 101;
	public static int ONLY_IN_DB = 102;
	public static int SAME = 100;
	public static int DIIF = 200;
	public static int NEWER = 201;
	public static int OLDER = 199;
	public static int UNDEFINED = -1;
	public static int ERROR = -100;
	public static int EMPTY = -200;

	private T entity;
	private boolean success = false;
	private String message = null;
	private int statusCode = UNDEFINED;
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public T getEntity() {
		return entity;
	}
	public void setEntity(T entity) {
		this.entity = entity;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		String s = "";
		if(this.isSuccess()){
			s = this.getEntity().toString();
		}
		else{
			s = "Failed: " + this.getMessage();
		}
		return s;
	}
	
}
