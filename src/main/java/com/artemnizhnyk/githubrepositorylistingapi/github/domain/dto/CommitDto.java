package com.artemnizhnyk.githubrepositorylistingapi.github.domain.dto;

import lombok.Getter;

@Getter
public class CommitDto {

    private Commit commit;
    private String sha;

    public record Commit(String message) {
    }
}
