package org.example.flingblogs.repository;

import org.example.flingblogs.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByUserId(Long userId);

    List<Article> findByIsPrivateFalse();
}
