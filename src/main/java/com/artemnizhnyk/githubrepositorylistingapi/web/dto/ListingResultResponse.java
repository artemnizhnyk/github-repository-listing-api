package com.artemnizhnyk.githubrepositorylistingapi.web.dto;

import lombok.Builder;

import java.net.URL;
import java.util.List;

@Builder
public class ListingResultResponse {

    private String repositoryName;
    private String ownerLogin;
    private List<Branch> branches;

    @Builder
    public static class Branch {

        private String name;
        private String lastCommitMessage;
        private String lastCommitUrl;
    }
}
