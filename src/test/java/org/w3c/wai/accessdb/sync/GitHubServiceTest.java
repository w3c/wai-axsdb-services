package org.w3c.wai.accessdb.sync;

import junit.framework.Assert;

import org.junit.Test;
import org.w3c.wai.accessdb.sync.om.GitHubTechniqueInfo;

public class GitHubServiceTest {

	@Test
	public void test() throws Exception {
		GitHubTechniqueInfo info = GitHubService.INSTANCE.getTechniqueGitMeta("aria/ARIA1.xml");
		
		Assert.assertEquals(info.getSha()==null, false);
		System.out.print(info);
	}

}
