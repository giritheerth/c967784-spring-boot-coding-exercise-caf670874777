package com.telstra.codechallenge.service;

import com.telstra.codechallenge.constant.GitRepositoryConstants;
import com.telstra.codechallenge.dto.GitRepositoryAPIResponse;
import com.telstra.codechallenge.exception.GitDataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.Objects;

import static com.telstra.codechallenge.util.GitRepositoryUtil.getDateToProcess;
import static com.telstra.codechallenge.util.GitRepositoryUtil.getLastLocalDate;

@Service
@Slf4j
public class GitRepositoryResponseService {
    @Value("${gitRepo.base.url}")
    private String gitRepositoryBaseUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public GitRepositoryResponseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public GitRepositoryAPIResponse getHottestGitRepositories(int noOfRepos, int lastDays) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        UriComponentsBuilder builder = getUriComponentsBuilder(gitRepositoryBaseUrl, noOfRepos, lastDays);
        ResponseEntity<GitRepositoryAPIResponse> response = this.restTemplate.exchange(
                builder.build().toString(), HttpMethod.GET, new HttpEntity<>(headers),
                GitRepositoryAPIResponse.class);
        log.info("getHottestGitRepositories Response is {}",response.getStatusCode());
        if (response.getStatusCode().is2xxSuccessful()) {
            if(Objects.requireNonNull(response.getBody()).getItems()!=null && !response.getBody().getItems().isEmpty()) {
                log.info("HottestGitRepositories Response count is {}", (long) response.getBody().getItems().size());
            }
            return response.getBody();
        }

        throw new GitDataNotFoundException("Unable to retrieve the repositories");

    }

    private UriComponentsBuilder getUriComponentsBuilder(String url, int noOfRepos, int lastDays) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParam(GitRepositoryConstants.REPO_QUERY, GitRepositoryConstants.REPO_CREATED_DATE + getDateToProcess(getLastLocalDate(lastDays)))
                .queryParam(GitRepositoryConstants.REPO_SORT_KEY, GitRepositoryConstants.REPO_STARS)
                .queryParam(GitRepositoryConstants.REPO_ORDER_KEY, GitRepositoryConstants.REPO_DESC)
                .queryParam(GitRepositoryConstants.REPO_PER_PAGE_LIMIT, noOfRepos);
    }

}
