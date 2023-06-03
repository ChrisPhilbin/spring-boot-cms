package net.chrisphilbin.cms.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import net.chrisphilbin.cms.entity.Post;

public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByUserId(Long userId);
}
