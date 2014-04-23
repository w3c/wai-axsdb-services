package org.w3c.wai.accessdb.sync.om;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * @author Vlachogiannis
 *
 * @param <T>
 */
@XmlRootElement
public class ImportResponse<T>{
	public static int NOTDEFINED = -1;
	public static int SAME = 100;
	public static int ONLY_IN_WCAG = 101;
	public static int ONLY_IN_DB = 102;
	public static int NEWER = 201;
	public static int OLDER = 202;
	public static int OK = 200;
	public static int FAIL = 302;

	private T entity;
	private int statusCode = NOTDEFINED;
	
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
	@Override
	public String toString() {
		return "StatusCode: " + this.statusCode ;
	}	
}