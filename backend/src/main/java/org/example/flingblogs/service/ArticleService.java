package org.example.flingblogs.service;


import org.example.flingblogs.model.Article;
import org.example.flingblogs.model.User;
import org.example.flingblogs.repository.ArticleRepository;
import org.example.flingblogs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    // 创建文章需要作者权限
    public Article createArticle(String username, Article article) {
        Optional<User> userOpt = Optional.empty();

        if(username != null && !username.isEmpty()) {
            userOpt = userRepository.findByUsername(username);
        }

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        article.setUser(userOpt.get());
        return articleRepository.save(article);
    }

    public List<Article> getPublicArticles(){
        return articleRepository.findByIsPrivateFalse();
    }

    public List<Article> getUserArticles(String username){
        Optional<User> userOpt = Optional.empty();

        if(username != null && !username.isEmpty()) {
            userOpt = userRepository.findByUsername(username);
        }

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return articleRepository.findByUserOrderByCreatedAtDesc(userOpt.get());
    }

    // 根据ID查看文章，私密文章只有作者能看
    public Optional<Article> getArticleById(String username, Long id){
        Optional<Article> articleOpt = articleRepository.findById(id);
        if(articleOpt.isEmpty()) return Optional.empty();

        Article article = articleOpt.get();
        if(Boolean.TRUE.equals(article.getIsPrivate())){
            if(!article.getUser().getUsername().equals(username)){
                throw new RuntimeException("无权访问此私密文章");
            }
        }
        return Optional.of(article);
    }

    public void deleteArticle(String username, Long id){
        Optional<Article> article = articleRepository.findById(id);
        if(article.isEmpty()){
            throw new RuntimeException("文章不存在");
        }
        if(!article.get().getUser().getUsername().equals(username)){
            throw new RuntimeException("无权删除此文章");
        }

        articleRepository.deleteById(id);
    }

}
