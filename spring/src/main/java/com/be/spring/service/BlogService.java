package com.be.spring.service;


import com.be.spring.dto.AddArticleRequest;
import com.be.spring.entity.Article;
import com.be.spring.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }


//    // 글 전체 조회
//    public List<Article> findAll() {
//        return blogRepository.findAll();
//    }


}
