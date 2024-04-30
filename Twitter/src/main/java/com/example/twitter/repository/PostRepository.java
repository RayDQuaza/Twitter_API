package com.example.twitter.repository;


import com.example.twitter.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findById(Integer id);
    boolean existsById(Integer id);
    void deleteById(Integer id);
    List<Post> findAllByOrderByDateDesc();
}
