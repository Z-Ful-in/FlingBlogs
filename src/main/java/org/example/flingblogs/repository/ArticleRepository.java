package org.example.flingblogs.repository;

import org.example.flingblogs.model.Article;
import org.example.flingblogs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByUser(User user);

    List<Article> findByIsPrivateFalse();

    List<Article> findByUserOrderByCreatedAtDesc(User user);
}
