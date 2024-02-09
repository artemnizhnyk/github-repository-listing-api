package com.artemnizhnyk.githubrepositorylistingapi.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


@Getter
public class UserGitHubRepositoryDto {

    private String name;
    private Owner owner;
    @JsonProperty("branches_url")
    private String branchesUrl;
    private boolean fork;

    @Getter
    public class Owner {
        private String login;
    }
}
