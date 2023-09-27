package com.telstra.codechallenge.service;

import com.telstra.codechallenge.constant.GitRepositoryConstants;
import com.telstra.codechallenge.dto.GitRepositoryAPIResponse;
import com.telstra.codechallenge.dto.GitRepositoryDetails;
import com.telstra.codechallenge.service.GitRepositoryResponseService;
import com.telstra.codechallenge.util.GitRepositoryUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.mockito.Mockito.lenient;

@RunWith(MockitoJUnitRunner.class)
public class GitRepositoryResponseServiceTest {
    private static final String HTTP_GITURL = "http://giturl";
    private static final int NO_OF_REPOS = 10;
    private static final int LAST_DAYS = 7;
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GitRepositoryResponseService gitRepositoryResponseService;
    @Before
    public void setup() {
        ReflectionTestUtils.setField(gitRepositoryResponseService, "gitRepositoryBaseUrl", HTTP_GITURL);
    }

    @Test
    public void getHottestRepoForSuccess()
            throws Exception {
        GitRepositoryAPIResponse apiSearchResponse = new GitRepositoryAPIResponse();
        apiSearchResponse.getItems().add(getGitRepoDetail());
        Mockito.when(restTemplate
                        .exchange(getUriComponentsBuilder(HTTP_GITURL, NO_OF_REPOS, LAST_DAYS).build().toString(),
                                HttpMethod.GET, new HttpEntity<>(getHttpHeaders()),
                                GitRepositoryAPIResponse.class))
                .thenReturn(new ResponseEntity<>(apiSearchResponse, HttpStatus.OK));

        GitRepositoryAPIResponse response = gitRepositoryResponseService.getHottestGitRepositories(NO_OF_REPOS, LAST_DAYS);

        Assert.assertEquals(apiSearchResponse, response);
    }

    private GitRepositoryDetails getGitRepoDetail() {
        GitRepositoryDetails gitRepoDetail =  new GitRepositoryDetails();
        gitRepoDetail.setDescription("TestDescription");
        gitRepoDetail.setName("TestName");
        return gitRepoDetail;
    }

    @Test(expected = Exception.class)
    public void getHottestRepoForException()
            throws Exception {
        GitRepositoryAPIResponse apiSearchResponse = new GitRepositoryAPIResponse();

        lenient().when(restTemplate
                        .exchange(
                                getUriComponentsBuilder("http://Wrongurl", NO_OF_REPOS, LAST_DAYS).build().toString(),
                                HttpMethod.GET, new HttpEntity<>(getHttpHeaders()),
                                GitRepositoryAPIResponse.class))
                .thenReturn(new ResponseEntity<>(apiSearchResponse, HttpStatus.NOT_FOUND));
        gitRepositoryResponseService.getHottestGitRepositories(NO_OF_REPOS, LAST_DAYS);

    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    private UriComponentsBuilder getUriComponentsBuilder(String url, int noOfRepos, int lastDays) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParam(GitRepositoryConstants.REPO_QUERY, GitRepositoryConstants.REPO_CREATED_DATE + GitRepositoryUtil.getDateToProcess(GitRepositoryUtil.getLastLocalDate(lastDays)))
                .queryParam(GitRepositoryConstants.REPO_SORT_KEY, GitRepositoryConstants.REPO_STARS)
                .queryParam(GitRepositoryConstants.REPO_ORDER_KEY, GitRepositoryConstants.REPO_DESC)
                .queryParam(GitRepositoryConstants.REPO_PER_PAGE_LIMIT, noOfRepos);
    }
}
