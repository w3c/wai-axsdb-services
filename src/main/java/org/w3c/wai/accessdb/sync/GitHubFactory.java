package org.w3c.wai.accessdb.sync;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.w3c.wai.accessdb.sync.om.GitHubTechniqueInfo;

public class GitHubFactory {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	public static GitHubTechniqueInfo createGitHubTechniqueInfoFromStringJson(String json) throws JsonParseException, IOException, ParseException{
		GitHubTechniqueInfo info = new GitHubTechniqueInfo();
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getJsonFactory();
        JsonParser jsonParser = factory.createJsonParser(json);
        JsonNode node = mapper.readTree(jsonParser);
        // assuming 1st is always the latest 
        JsonNode commit = node.get(0);
        info.setSha(commit.get("sha").getTextValue());
        String dateS = commit.get("commit").get("author").get("date").getTextValue();
        info.setDate(GitHubFactory.dateFormat.parse(dateS));	
        return info;
	}
	
}
