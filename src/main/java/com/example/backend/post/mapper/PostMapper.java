package com.example.backend.post.mapper;

import com.example.backend.post.model.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMapper {

    List<Post> findAll();

    Optional<Post> findById(@Param("id") Long id);

    void insert(Post post);

    void update(Post post);

    void deleteById(@Param("id") Long id);
}
