package com.artemnizhnyk.githubrepositorylistingapi.web.dto;

import lombok.Getter;

@Getter
public class BranchDto {

    private String name;
    private Commit commit;

    @Getter
    public static class Commit {
        private String url;
    }
}
