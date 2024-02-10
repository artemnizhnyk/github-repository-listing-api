package com.artemnizhnyk.githubrepositorylistingapi.github.domain;

import com.artemnizhnyk.githubrepositorylistingapi.github.GitHubFacade;
import com.artemnizhnyk.githubrepositorylistingapi.github.domain.dto.BranchDto;
import com.artemnizhnyk.githubrepositorylistingapi.github.domain.dto.CommitDto;
import com.artemnizhnyk.githubrepositorylistingapi.github.domain.dto.ListingResultResponse;
import com.artemnizhnyk.githubrepositorylistingapi.github.domain.dto.UserRepositoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GitHubServiceImpl implements GitHubFacade {

    private final RestTemplate restTemplate;
    @Value("${github.auth.token}")
    private String githubAuthToken;

    public static final String REPOSITORIES_URL = "https://api.github.com/users/%s/repos";
    public static final String BRANCHES_URL = "https://api.github.com/repos/%s/%s/branches";
    public static final String AUTHORIZATION = "Authorization";

    @Override
    public List<ListingResultResponse> getListingResultResponses(final String username, final String authToken) {
        if (!authToken.isBlank()) {
            githubAuthToken = "Bearer " + authToken;
        }

        return getUserRepositories(username).stream()
                .filter(repo -> !repo.fork())
                .map(this::mapToListingResultResponse)
                .toList();
    }

    private ListingResultResponse mapToListingResultResponse(final UserRepositoryDto userRepositoryDto) {
        return ListingResultResponse.builder()
                .repositoryName(userRepositoryDto.name())
                .ownerLogin(userRepositoryDto.owner().login())
                .branchResponses(getListingResultResponseBranches(userRepositoryDto))
                .build();
    }

    private List<ListingResultResponse.BranchResponse> getListingResultResponseBranches(final UserRepositoryDto withoutForkUserRepository) {
        return getRepositoryBranches(
                withoutForkUserRepository.owner().login(),
                withoutForkUserRepository.name()
        )
                .stream()
                .map(this::mapToListingResultResponseBranch).toList();
    }

    private ListingResultResponse.BranchResponse mapToListingResultResponseBranch(final BranchDto branchDto) {
        CommitDto lastCommit = getBranchLastCommit(branchDto.commit().url());

        return ListingResultResponse.BranchResponse.builder()
                .name(branchDto.name())
                .lastCommitSha(lastCommit.getSha())
                .build();
    }

    private List<UserRepositoryDto> getUserRepositories(final String username) {

        ResponseEntity<UserRepositoryDto[]> response = restTemplate
                .exchange(
                        String.format(REPOSITORIES_URL, username),
                        HttpMethod.GET, getHttpEntityWithAuthToken(),
                        UserRepositoryDto[].class
                );

        if (response.getBody() == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(response.getBody());
    }

    private List<BranchDto> getRepositoryBranches(final String username, final String repositoryName) {

        ResponseEntity<BranchDto[]> response = restTemplate
                .exchange(
                        String.format(BRANCHES_URL, username, repositoryName),
                        HttpMethod.GET, getHttpEntityWithAuthToken(),
                        BranchDto[].class
                );
        if (response.getBody() == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(response.getBody());
    }

    private CommitDto getBranchLastCommit(final String branchUrl) {
        ResponseEntity<CommitDto> response = restTemplate
                .exchange(branchUrl, HttpMethod.GET, getHttpEntityWithAuthToken(), CommitDto.class);

        if (response.getBody() == null) {
            return new CommitDto();
        }
        return response.getBody();
    }

    private HttpEntity<?> getHttpEntityWithAuthToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, githubAuthToken);
        return new HttpEntity<>(headers);

    }
}
