package net.chrisphilbin.cms.service;

import java.security.Principal;
import java.util.List;

import net.chrisphilbin.cms.entity.Post;

public interface PostService {
    Post getPost(Long id);
    Post savePost(Post todo);
    Post updatePost(Post newPost, Post oldPost);
    void deletePost(Long id);
    List<Post> getPosts(Long userId);
    Boolean verifyPostBelongsToUser(Post todo, Principal principal);
}
