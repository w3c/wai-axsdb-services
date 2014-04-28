package org.w3c.wai.accessdb.sync;

import java.io.IOException;
import java.util.List;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.w3c.wai.accessdb.services.ConfigService;
import org.w3c.wai.accessdb.sync.om.GitHubTechniqueInfo;

public enum GitHubService {
	INSTANCE;
	private String username = "";
	private String password = "";
	private String techniquesBasePath = "wcag20/sources/techniques/";
	private String branchNameDefault = "Working-Branch-for-Fall-2014";
	private String branchName = null;
	private CommitService commitService = null;
	private String branchSha = null;
	private Repository repository = null;
	private RepositoryService repoService;
 
	public void init(String branch) throws IOException {
		this.username = ConfigService.INSTANCE.getConfigParam("github.username");
		this.password = ConfigService.INSTANCE.getConfigParam("github.password");
		this.techniquesBasePath = ConfigService.INSTANCE.getConfigParam("github.techniquesBasePath");
		this.branchNameDefault = ConfigService.INSTANCE.getConfigParam("github.branchNameDefault");
		this.branchName = branch;
		this.commitService = new CommitService();
		commitService.getClient().setCredentials(this.username, this.password);
		repoService = new RepositoryService();
		repoService.getClient().setCredentials(this.username, this.password);
		this.repository = repoService.getRepository("w3c", "wcag");
		this.getRepoBranch(this.branchName);
	}

	public GitHubTechniqueInfo getTechniqueGitMeta(String techniquePath) throws Exception {
		if(this.repository==null){
			this.init(branchNameDefault);
		}
		List<RepositoryCommit> commits = this.commitService.getCommits(
				this.repository, this.branchSha, this.techniquesBasePath +  techniquePath);
		RepositoryCommit commit = commits.get(0);
		GitHubTechniqueInfo info = new GitHubTechniqueInfo();
		info.setDate(commit.getCommit().getAuthor().getDate());
		info.setSha(commit.getSha());
		info.setUrl(commit.getUrl());
		info.setDiffUrl(this.repository.getHtmlUrl() + "/commit/" + commit.getSha()); //FIXME: with API see: RepositoryCommitCompare 
		return info;

	}
	public RepositoryBranch getRepoBranch(String name) throws IOException{
		List<RepositoryBranch> repositoryBranchs = repoService
				.getBranches(this.repository);
		for (RepositoryBranch repositoryBranch : repositoryBranchs) {
			if (repositoryBranch.getName().equals(this.branchName)) {
				this.branchSha = repositoryBranch.getCommit().getSha();
				return repositoryBranch;
			}
		}
		return null;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

}
