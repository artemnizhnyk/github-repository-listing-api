package com.artemnizhnyk.githubrepositorylistingapi.github.controller;

import com.artemnizhnyk.githubrepositorylistingapi.github.GitHubFacade;
import com.artemnizhnyk.githubrepositorylistingapi.github.domain.dto.ListingResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/github")
@RestController
class GitHubController {

    final GitHubFacade gitHubService;

    @GetMapping("/{username}/repositories")
    List<ListingResultResponse> listRepositories(@PathVariable final String username,
                                                 @RequestParam(required = false, defaultValue = "") final String authToken) {
        return gitHubService.getListingResultResponses(username, authToken);
    }
}
