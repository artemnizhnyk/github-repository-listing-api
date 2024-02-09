package com.artemnizhnyk.githubrepositorylistingapi.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


@Getter
public class UserRepositoryDto {

    private String name;
    private Owner owner;
    @JsonProperty("branches_url")
    private String branchesUrl;
    private boolean fork;

    @Getter
    public static class Owner {
        private String login;
    }
}
