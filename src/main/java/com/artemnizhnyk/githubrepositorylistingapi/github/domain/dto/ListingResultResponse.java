package com.artemnizhnyk.githubrepositorylistingapi.github.domain.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ListingResultResponse(String repositoryName,
                                    String ownerLogin,
                                    List<BranchResponse> branchResponses
) {
    @Builder
    public record BranchResponse(String name, String lastCommitSha) {
    }
}
