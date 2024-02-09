package com.artemnizhnyk.githubrepositorylistingapi.service.impl;

import com.artemnizhnyk.githubrepositorylistingapi.exception.NotFoundException;
import com.artemnizhnyk.githubrepositorylistingapi.service.GitHubService;
import com.artemnizhnyk.githubrepositorylistingapi.web.dto.BranchDto;
import com.artemnizhnyk.githubrepositorylistingapi.web.dto.CommitDto;
import com.artemnizhnyk.githubrepositorylistingapi.web.dto.ListingResultResponse;
import com.artemnizhnyk.githubrepositorylistingapi.web.dto.UserRepositoryDto;
import lombok.RequiredArgsConstructor;
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

    @Override
    public List<ListingResultResponse> getListingResultResponse(final String username) {

        List<UserRepositoryDto> userRepositories = getUserRepositories(username);
        List<UserRepositoryDto> withoutForkUserRepositories = getNotForkRepositories(userRepositories);

        List<ListingResultResponse> response = new ArrayList<>();

        for (UserRepositoryDto withoutForkUserRepository : withoutForkUserRepositories) {

            List<ListingResultResponse.Branch> branches = new ArrayList<>();

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
        return getRepositoryBranches(withoutForkUserRepository.getName())
                .stream()

                .map(branchDto -> ListingResultResponse.Branch.builder()
                        .name(branchDto.getName())
                        .lastCommitMessage(getBranchLastCommit(branchDto.getCommit().getUrl()).getCommit().getMessage())
                        .lastCommitUrl(getBranchLastCommit(branchDto.getCommit().getUrl()).getUrl())
                        .build()
                ).toList();
    }

    @Override
    public List<UserRepositoryDto> getUserRepositories(final String username) {
        String url = "https://api.github.com/users/" + username + "/repos";
        ResponseEntity<UserRepositoryDto[]> response;
        try {
            response = restTemplate
                    .exchange(url, HttpMethod.GET, null, UserRepositoryDto[].class);
        } catch (Exception e) {
            throw new NotFoundException(String.format("GitHub with username: %s wasn't found", username));
        }


        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    @Override
    public List<BranchDto> getRepositoryBranches(final String repositoryName) {
        String url = "https://api.github.com/repos/artemnizhnyk/" + repositoryName + "/branches";
        ResponseEntity<BranchDto[]> response;
        try {
            response = restTemplate
                    .exchange(url, HttpMethod.GET, null, BranchDto[].class);
        } catch (Exception e) {
            throw new NotFoundException(String.format("Repository named: %s wasn't found", repositoryName));

        }
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    @Override
    public CommitDto getBranchLastCommit(final String branchUrl) {
        ResponseEntity<CommitDto> response;
        try {
            response = restTemplate
                    .exchange(branchUrl, HttpMethod.GET, null, CommitDto.class);
        } catch (Exception e) {
            throw new NotFoundException(String.format("Branch with url: %s wasn't found", branchUrl));

        }
        return Objects.requireNonNull(response.getBody());
    }

    private List<UserRepositoryDto> getNotForkRepositories(final List<UserRepositoryDto> userRepositories) {
        return userRepositories.stream().filter(it -> !it.isFork()).toList();
    }
}
