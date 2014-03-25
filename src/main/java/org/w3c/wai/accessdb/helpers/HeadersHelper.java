package org.w3c.wai.accessdb.helpers;

import java.io.IOException;

import javax.ws.rs.core.HttpHeaders;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
@Deprecated
public class HeadersHelper {

	public static String headers2JSON (HttpHeaders headers)
	{
		ObjectMapper mapper = new ObjectMapper();
		String httpHeaders=null;
		try {
			httpHeaders = mapper.writeValueAsString(headers);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return httpHeaders;
	}
	public static HttpHeaders JSON2Headers (String headersS)
	{
		ObjectMapper mapper = new ObjectMapper();
		HttpHeaders headers = null;
		try {
			headers = mapper.readValue(headersS, HttpHeaders.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return headers;
	}
	
}
