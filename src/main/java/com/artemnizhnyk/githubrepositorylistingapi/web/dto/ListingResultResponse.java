package com.artemnizhnyk.githubrepositorylistingapi.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ListingResultResponse {

    private String repositoryName;
    private String ownerLogin;
    private List<Branch> branches;

    @Data
    @Builder
    public static class Branch {

        private String name;
        private String lastCommitMessage;
        private String lastCommitUrl;
        private String lastCommitSha;
    }
}
