package com.example.gitHub.controller;

import com.example.gitHub.dto.GitHubSearchRequest;
import com.example.gitHub.entity.RepositoryEntity;
import com.example.gitHub.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/github")
@RequiredArgsConstructor
public class GitHubController {

    private final GitHubService githubService;

    // 🔸 POST /api/github/search
    @PostMapping("/search")
    public ResponseEntity<?> searchRepositories(@RequestBody GitHubSearchRequest request) {
        List<RepositoryEntity> repos = githubService.searchAndSaveRepositories(request);
        return ResponseEntity.ok(Map.of(
                "message", "Repositories fetched and saved successfully",
                "repositories", repos
        ));
    }

    // 🔸 GET /api/github/repositories
    @GetMapping("/repositories")
    public ResponseEntity<?> getRepositories(
            @RequestParam(required = false) String language,
            @RequestParam(required = false) Integer minStars,
            @RequestParam(required = false) String sort
    ) {
        List<RepositoryEntity> repos = githubService.getFilteredRepositories(language, minStars, sort);
        return ResponseEntity.ok(Map.of("repositories", repos));
    }
}
