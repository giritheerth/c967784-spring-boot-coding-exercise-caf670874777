package com.telstra.codechallenge.controller;

import com.telstra.codechallenge.constant.GitRepositoryConstants;
import com.telstra.codechallenge.dto.GitRepositoryAPIResponse;
import com.telstra.codechallenge.service.GitRepositoryResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/git")
public class GitRepositoryController {
    private final GitRepositoryResponseService getRepositoryResponseService;

    @Autowired
    public GitRepositoryController(
            GitRepositoryResponseService getRepositoryResponseService) {
        this.getRepositoryResponseService = getRepositoryResponseService;
    }

    /**
     * getHottestRepos method takes no of Repos to be listed in the response from the API call.
     */
    @GetMapping("/hottest/{noOfRepos}")
    public ResponseEntity<GitRepositoryAPIResponse> getHottestRepos(@PathVariable int noOfRepos) {
        if (noOfRepos < 0) {
            throw new IllegalArgumentException("noOfRepos should not be a negative");
        }
        return ResponseEntity.ok(getRepositoryResponseService.getHottestGitRepositories(noOfRepos, GitRepositoryConstants.NO_WEEK_DAYS));
    }
}
