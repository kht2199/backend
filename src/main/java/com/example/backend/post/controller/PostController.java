package com.example.backend.post.controller;

import com.example.backend.post.dto.PostRequestDto;
import com.example.backend.post.dto.PostResponseDto;
import com.example.backend.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Post", description = "게시글 CRUD API (MyBatis)")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "전체 게시글 조회")
    @GetMapping
    public List<PostResponseDto> findAll() {
        return postService.findAll();
    }

    @Operation(summary = "게시글 단건 조회")
    @GetMapping("/{id}")
    public PostResponseDto findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @Operation(summary = "게시글 생성")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseDto create(@Valid @RequestBody PostRequestDto request) {
        return postService.create(request);
    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/{id}")
    public PostResponseDto update(@PathVariable Long id, @Valid @RequestBody PostRequestDto request) {
        return postService.update(id, request);
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        postService.delete(id);
    }
}
