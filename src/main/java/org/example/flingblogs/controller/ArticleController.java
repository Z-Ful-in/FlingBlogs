package org.example.flingblogs.controller;


import org.example.flingblogs.model.Article;
import org.example.flingblogs.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
            @RequestHeader ("Authorization") String token,
            @RequestBody Article article){
        Article createdArticle = articleService.createArticle(token.substring(7), article);
        return ResponseEntity.ok(createdArticle);
    }

    @GetMapping("/public")
    public ResponseEntity<List<Article>> getPublicArticles(){
        List<Article> articles = articleService.getPublicArticles();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/me")
    public ResponseEntity<List<Article>> getMyArticles(
            @RequestHeader ("Authorization") String token){
        List<Article> articles = articleService.getUserArticles(token.substring(7));
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticle( // 未登录也可以查看公开文章
            @RequestHeader (value="Authorization",required = false) String token,
            @PathVariable Long id){
        return articleService.getArticleById(token.substring(7), id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteArticle(
            @RequestHeader ("Authorization") String token,
            @PathVariable Long id){
        articleService.deleteArticle(token.substring(7), id);
        return ResponseEntity.ok(Map.of("message", "文章删除成功"));
    }

}
