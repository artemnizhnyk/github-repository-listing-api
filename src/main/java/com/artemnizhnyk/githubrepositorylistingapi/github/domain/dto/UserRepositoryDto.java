package com.artemnizhnyk.githubrepositorylistingapi.github.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserRepositoryDto(String name,
                                OwnerDto owner,
                                @JsonProperty("branches_url")
                                String branchesUrl,
                                boolean fork
) {
    public record OwnerDto(String login) {
    }
}
