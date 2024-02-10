package com.artemnizhnyk.githubrepositorylistingapi.github;

import com.artemnizhnyk.githubrepositorylistingapi.github.domain.dto.ListingResultResponse;

import java.util.List;

public interface GitHubFacade {

    List<ListingResultResponse> getListingResultResponses(final String username, final String authToken);
}
