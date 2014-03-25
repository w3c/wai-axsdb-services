package org.w3c.wai.accessdb;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.w3c.wai.accessdb.jaxb.TestingSession;

public class TestTestingSession {
 public static void main(String[] args) throws IOException {
	 String p ="/home/evlach/session.txt";
		File f = new File(p);
		//TestTestingSession.class.getResource("session.json").getPath()
		
		ObjectMapper mapper = new ObjectMapper();
		TestingSession s = mapper.readValue(f, TestingSession.class);
		//Map<String,Object> userData = mapper.readValue(f, Map.class);

		//System.out.print(userData.get("testProfileId"));
		System.out.print(s.getSessionId());
		
}
}
