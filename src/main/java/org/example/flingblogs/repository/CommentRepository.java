package org.example.flingblogs.repository;

import org.example.flingblogs.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleId(Long articleId);

    List<Comment> findByUserId(Long userId);
}
