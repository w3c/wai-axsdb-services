package org.w3c.wai.accessdb.services;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public enum SVNService {
	INSTANCE;
	private static final Logger logger = LoggerFactory
			.getLogger(SVNService.class);
	private String url = "https://github.com/evlach/testing/trunk/";
	private String name = "evlach";
	private String password = "";
	private SVNRepository repository = null;
	private static SVNClientManager clientManager;

	public static void main(String[] args) throws SVNException {
		File testFolder = new File("/home/evlach/test/"); 
	//	SVNService.INSTANCE.importDirectory(testFolder,SVNURL
		//	.parseURIEncoded(SVNService.INSTANCE.url), "new ver",true);
		SVNService.INSTANCE.commit(testFolder, false, "new versdf");

	}
	
	private SVNService() {
		// this.url = ConfigService.INSTANCE.getConfigParam("")
		// TODO
		this.initRepo();
	}

	public void initRepo() {		
		try {
			DAVRepositoryFactory.setup();
			repository = SVNRepositoryFactory.create(SVNURL
					.parseURIEncoded(url));
			ISVNAuthenticationManager authManager = SVNWCUtil
					.createDefaultAuthenticationManager(name, password);
			repository.setAuthenticationManager(authManager);
			ISVNOptions options = SVNWCUtil.createDefaultOptions( true );

			clientManager = SVNClientManager.newInstance(options, authManager);
			
		} catch (SVNException svne) {
			logger.error("while creating an SVNRepository for the location '"
					+ url + "': " + svne.getMessage());
		}
	}

	public void testInRepoCommit(File testFolder) throws SVNException {
	}

	// imports a local directory to a repository
	private static SVNCommitInfo importDirectory(File localPath, SVNURL dstURL,
			String commitMessage, boolean isRecursive) throws SVNException {
		return clientManager.getCommitClient().doImport(localPath, dstURL,
				commitMessage, isRecursive);
	}

	private static SVNCommitInfo commit(File wcPath, boolean keepLocks,
			String commitMessage) throws SVNException {
		return clientManager.getCommitClient().doCommit(
				new File[] { wcPath }, keepLocks, commitMessage, true, true);
	}
}