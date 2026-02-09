package com.example.backend.post.service;

import com.example.backend.post.dto.PostRequestDto;
import com.example.backend.post.dto.PostResponseDto;
import com.example.backend.post.mapper.PostMapper;
import com.example.backend.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostMapper postMapper;

    public List<PostResponseDto> findAll() {
        return postMapper.findAll().stream()
                .map(PostResponseDto::from)
                .toList();
    }

    public PostResponseDto findById(Long id) {
        Post post = postMapper.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post not found: id=" + id));
        return PostResponseDto.from(post);
    }

    @Transactional
    public PostResponseDto create(PostRequestDto request) {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(request.getAuthor())
                .build();
        postMapper.insert(post);
        return PostResponseDto.from(
                postMapper.findById(post.getId())
                        .orElseThrow(() -> new NoSuchElementException("Post insert failed"))
        );
    }

    @Transactional
    public PostResponseDto update(Long id, PostRequestDto request) {
        Post post = postMapper.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post not found: id=" + id));
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(request.getAuthor());
        postMapper.update(post);
        return PostResponseDto.from(
                postMapper.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("Post update failed"))
        );
    }

    @Transactional
    public void delete(Long id) {
        postMapper.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post not found: id=" + id));
        postMapper.deleteById(id);
    }
}
