package com.example.gitHub;

import com.example.gitHub.dto.GitHubSearchRequest;
import com.example.gitHub.entity.RepositoryEntity;
import com.example.gitHub.repository.RepositoryRepository;
import com.example.gitHub.service.GitHubService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class GitHubServiceTest {

    @MockBean
    private RepositoryRepository repositoryRepo;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private GitHubService gitHubService;

    @Test
    public void testSearchAndSaveRepositories() {
        GitHubSearchRequest request = new GitHubSearchRequest();
        request.setQuery("spring boot");
        request.setLanguage("Java");
        request.setSort("stars");

        Map<String, Object> fakeRepo = Map.of(
                "id", 1,
                "name", "demo",
                "description", "desc",
                "language", "Java",
                "stargazers_count", 100,
                "forks_count", 10,
                "updated_at", "2024-01-01T12:00:00Z",
                "owner", Map.of("login", "testuser")
        );

        Map<String, Object> fakeResponse = Map.of("items", List.of(fakeRepo));

        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.isNull(),
                ArgumentMatchers.eq(Map.class)
        )).thenReturn(new ResponseEntity<>(fakeResponse, HttpStatus.OK));

        List<RepositoryEntity> result = gitHubService.searchAndSaveRepositories(request);
        Assertions.assertEquals(1, result.size());
    }
}

