package com.example.gitHub;

import com.example.gitHub.controller.GitHubController;
import com.example.gitHub.entity.RepositoryEntity;
import com.example.gitHub.service.GitHubService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GitHubController.class)
public class GitHubControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GitHubService gitHubService;

    @Test
    public void testGetRepositories() throws Exception {
        RepositoryEntity entity = new RepositoryEntity(1L, "demo", "desc", "user", "Java", 100, 10, Instant.now());
        Mockito.when(gitHubService.getFilteredRepositories(null, null, null))
                .thenReturn(List.of(entity));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/github/repositories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.repositories[0].name").value("demo"));
    }
}

