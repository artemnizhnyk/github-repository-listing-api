package com.artemnizhnyk.githubrepositorylistingapi.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CommitDto {

    private Commit commit;
    @JsonProperty("html_url")
    private String url;
    private String sha;

    @Getter
    public static class Commit {

        private String message;
    }
}
