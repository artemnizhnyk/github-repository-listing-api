package com.artemnizhnyk.githubrepositorylistingapi.service.impl;

import com.artemnizhnyk.githubrepositorylistingapi.service.GitHubService;
import com.artemnizhnyk.githubrepositorylistingapi.web.dto.BranchDto;
import com.artemnizhnyk.githubrepositorylistingapi.web.dto.CommitDto;
import com.artemnizhnyk.githubrepositorylistingapi.web.dto.ListingResultResponse;
import com.artemnizhnyk.githubrepositorylistingapi.web.dto.UserRepositoryDto;
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
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class GitHubServiceImpl implements GitHubService {

    private final RestTemplate restTemplate;
    @Value("${github.auth.token}")
    private String GITHUB_AUTH_TOKEN;

    @Override
    public List<ListingResultResponse> getListingResultResponse(final String username, final String authToken) {
        if (!authToken.isBlank()) {
            GITHUB_AUTH_TOKEN = "Bearer " + authToken;
        }

        List<UserRepositoryDto> userRepositories = getUserRepositories(username);
        List<UserRepositoryDto> withoutForkUserRepositories = getNotForkRepositories(userRepositories);

        List<ListingResultResponse> response = new ArrayList<>();

        for (UserRepositoryDto withoutForkUserRepository : withoutForkUserRepositories) {

            response.add(ListingResultResponse.builder()
                    .repositoryName(withoutForkUserRepository.getName())
                    .ownerLogin(withoutForkUserRepository.getOwner().getLogin())
                    .branches(getList(withoutForkUserRepository))
                    .build()
            );
        }

        return response;

    }

    private List<ListingResultResponse.Branch> getList(final UserRepositoryDto withoutForkUserRepository) {
        return getRepositoryBranches(
                withoutForkUserRepository.getOwner().getLogin(),
                withoutForkUserRepository.getName()
        )
                .stream()

                .map(branchDto -> {
                            CommitDto lastCommit = getBranchLastCommit(branchDto.getCommit().getUrl());

                            return ListingResultResponse.Branch.builder()
                                    .name(branchDto.getName())
                                    .lastCommitSha(lastCommit.getSha())
                                    .build();
                        }
                ).toList();
    }

    @Override
    public List<UserRepositoryDto> getUserRepositories(final String username) {
        String url = "https://api.github.com/users/" + username + "/repos";

        ResponseEntity<UserRepositoryDto[]> response = restTemplate
                .exchange(url, HttpMethod.GET, getHttpEntityWithAuthToken(), UserRepositoryDto[].class);

        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    @Override
    public List<BranchDto> getRepositoryBranches(final String username, final String repositoryName) {
        String url = "https://api.github.com/repos/" + username + "/" + repositoryName + "/branches";

        ResponseEntity<BranchDto[]> response = restTemplate
                .exchange(url, HttpMethod.GET, getHttpEntityWithAuthToken(), BranchDto[].class);

        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    @Override
    public CommitDto getBranchLastCommit(final String branchUrl) {
        ResponseEntity<CommitDto> response = restTemplate
                .exchange(branchUrl, HttpMethod.GET, getHttpEntityWithAuthToken(), CommitDto.class);

        return Objects.requireNonNull(response.getBody());
    }

    private HttpEntity<?> getHttpEntityWithAuthToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", GITHUB_AUTH_TOKEN);
        return new HttpEntity<>(headers);

    }

    private List<UserRepositoryDto> getNotForkRepositories(final List<UserRepositoryDto> userRepositories) {
        return userRepositories.stream().filter(it -> !it.isFork()).toList();
    }
}
