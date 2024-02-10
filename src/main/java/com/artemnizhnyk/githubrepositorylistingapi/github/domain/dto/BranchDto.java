package com.artemnizhnyk.githubrepositorylistingapi.github.domain.dto;

public record BranchDto(String name, Commit commit) {
    public record Commit(String url) {
    }
}
