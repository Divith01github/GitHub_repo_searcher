package com.example.gitHub.dto;

import lombok.Data;

@Data
public class GitHubSearchRequest {
    private String query;
    private String language;
    private String sort; // values: "stars", "forks", "updated"
}
