package com.artemnizhnyk.githubrepositorylistingapi.service;

import com.artemnizhnyk.githubrepositorylistingapi.web.dto.BranchDto;
import com.artemnizhnyk.githubrepositorylistingapi.web.dto.CommitDto;
import com.artemnizhnyk.githubrepositorylistingapi.web.dto.ListingResultResponse;
import com.artemnizhnyk.githubrepositorylistingapi.web.dto.UserRepositoryDto;

import java.util.List;

public interface GitHubService {

    List<ListingResultResponse> getListingResultResponse(final String username, final String authToken);

    List<UserRepositoryDto> getUserRepositories(final String username);

    List<BranchDto> getRepositoryBranches(final String username, final String repositoryName);

    CommitDto getBranchLastCommit(final String branchUrl);
}
