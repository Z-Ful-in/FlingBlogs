package org.example.flingblogs.controller;


import org.example.flingblogs.model.Article;
import org.example.flingblogs.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseEntity<Article> createArticle(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Article article){
        String username = (userDetails != null) ? userDetails.getUsername(): null; // 本质还是从SecurityContextHolder中获取用户名
        Article createdArticle = articleService.createArticle(username, article);
        return ResponseEntity.ok(createdArticle);
    }

    @GetMapping("/public")
    public ResponseEntity<List<Article>> getPublicArticles(){
        List<Article> articles = articleService.getPublicArticles();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/me")
    public ResponseEntity<List<Article>> getMyArticles(
           @AuthenticationPrincipal UserDetails userDetails) {
        String username = (userDetails != null) ? userDetails.getUsername() : null;
        List<Article> articles = articleService.getUserArticles(username);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable Long id,
                                              @AuthenticationPrincipal UserDetails userDetails) { // 未登录也可以查看公开的文章
        String username = (userDetails != null) ? userDetails.getUsername() : null;
        return articleService.getArticleById(username, id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteArticle(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id){
        String username = (userDetails != null) ? userDetails.getUsername(): null;
        articleService.deleteArticle(username, id);
        return ResponseEntity.ok(Map.of("message", "文章删除成功"));
    }

}
