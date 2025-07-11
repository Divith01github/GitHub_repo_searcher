package com.example.gitHub.service;

import com.example.gitHub.dto.GitHubSearchRequest;
import com.example.gitHub.entity.RepositoryEntity;
import com.example.gitHub.repository.RepositoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GitHubService {

    private final RestTemplate restTemplate;
    private final RepositoryRepository repositoryRepo;

    private static final String GITHUB_SEARCH_API = "https://api.github.com/search/repositories";

    public List<RepositoryEntity> searchAndSaveRepositories(GitHubSearchRequest request) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(GITHUB_SEARCH_API)
                .queryParam("q", request.getQuery() + "+language:" + request.getLanguage())
                .queryParam("sort", request.getSort());

        ResponseEntity<Map> response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                null,
                Map.class
        );

        List<Map<String, Object>> items = (List<Map<String, Object>>) response.getBody().get("items");

        if (items == null) throw new RuntimeException("GitHub API response invalid or empty");

        List<RepositoryEntity> savedRepos = new ArrayList<>();

        for (Map<String, Object> item : items) {
            Map<String, Object> owner = (Map<String, Object>) item.get("owner");

            RepositoryEntity repo = new RepositoryEntity();
            repo.setId(Long.parseLong(item.get("id").toString()));
            repo.setName((String) item.get("name"));
            repo.setDescription((String) item.get("description"));
            repo.setOwner(owner.get("login").toString());
            repo.setLanguage((String) item.get("language"));
            repo.setStars((int) item.get("stargazers_count"));
            repo.setForks((int) item.get("forks_count"));
            repo.setLastUpdated(Instant.parse((String) item.get("updated_at")));

            savedRepos.add(repositoryRepo.save(repo));
        }

        return savedRepos;
    }

    public List<RepositoryEntity> getFilteredRepositories(String language, Integer minStars, String sortBy) {
        Sort sort = Sort.by(sortBy != null ? sortBy : "stars").descending();

        if (language != null && minStars != null) {
            return repositoryRepo.findByLanguageIgnoreCaseAndStarsGreaterThanEqual(language, minStars, sort);
        } else if (language != null) {
            return repositoryRepo.findByLanguageIgnoreCase(language, sort);
        } else if (minStars != null) {
            return repositoryRepo.findByStarsGreaterThanEqual(minStars, sort);
        } else {
            return repositoryRepo.findAll(sort);
        }
    }
}
