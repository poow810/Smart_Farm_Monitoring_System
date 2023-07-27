package com.be.spring.service;


import com.be.spring.dto.AddArticleRequest;
import com.be.spring.dto.UpdateArticleRequest;
import com.be.spring.entity.Article;
import com.be.spring.repository.BlogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    // 글 저장
    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }


    // 글 전체 조회
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    // 해당 id 글 조회
    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    // 글 삭제
    public void delete(long id) {
        blogRepository.deleteById(id);
    }

    // 글 수정
    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found :" + id));
        article.update(request.getTitle(), request.getContent());
        return article;
    }


}
