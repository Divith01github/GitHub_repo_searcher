package com.example.gitHub.repository;

import com.example.gitHub.entity.RepositoryEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositoryRepository extends JpaRepository<RepositoryEntity, Long> {
    List<RepositoryEntity> findByLanguageIgnoreCaseAndStarsGreaterThanEqual(String language, int minStars, Sort sort);
    List<RepositoryEntity> findByLanguageIgnoreCase(String language, Sort sort);
    List<RepositoryEntity> findByStarsGreaterThanEqual(int minStars, Sort sort);
}

