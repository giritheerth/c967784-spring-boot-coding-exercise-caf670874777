package com.telstra.codechallenge.controller;

import com.telstra.codechallenge.MicroserviceApplication;
import com.telstra.codechallenge.config.ExceptionAdvice;
import com.telstra.codechallenge.dto.GitRepositoryAPIResponse;
import com.telstra.codechallenge.service.GitRepositoryResponseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {MicroserviceApplication.class})
public class GitRepositoryControllerTest {
    private static final String GIT_SAMPLE_REQUEST_5 = "/git/hottest/5";
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @InjectMocks
    private GitRepositoryController gitSearchResponseController;

    @Mock
    private GitRepositoryResponseService gitSearchResponseService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(gitSearchResponseController)
                .setControllerAdvice(new ExceptionAdvice())
                .build();
    }

    @Test
    public void getHottestRepos_success() throws Exception {
        when(gitSearchResponseService.getHottestGitRepositories(5, 10)).thenReturn(new GitRepositoryAPIResponse());
        mockMvc.perform(get(GIT_SAMPLE_REQUEST_5)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getHottestRepos_failure() throws Exception {
        when(gitSearchResponseService.getHottestGitRepositories(5, 7)).thenThrow(HttpClientErrorException.class);
        mockMvc.perform(get(GIT_SAMPLE_REQUEST_5)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
