package com.artemnizhnyk.githubrepositorylistingapi.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.net.URL;

@Getter
public class BranchDto {

    private String name;
    private Commit commit;

    @Getter
    public static class Commit {
        private String url;
    }
}
