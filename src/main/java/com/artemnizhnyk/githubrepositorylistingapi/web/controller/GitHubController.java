package com.artemnizhnyk.githubrepositorylistingapi.web.controller;

import com.artemnizhnyk.githubrepositorylistingapi.service.GitHubService;
import com.artemnizhnyk.githubrepositorylistingapi.web.dto.ListingResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/github")
@RestController
public class GitHubController {

    private final GitHubService gitHubService;

    @GetMapping("/{username}/repositories")
    public ResponseEntity<?> listRepositories(@PathVariable final String username,
                                              @RequestParam(required = false, defaultValue = "") final String authToken) {
        List<ListingResultResponse> response = gitHubService.getListingResultResponse(username, authToken);
        return ResponseEntity.ok(response);
    }
}
