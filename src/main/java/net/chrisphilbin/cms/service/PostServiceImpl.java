package net.chrisphilbin.cms.service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.chrisphilbin.cms.entity.Category;
import net.chrisphilbin.cms.entity.Post;
import net.chrisphilbin.cms.exception.EntityNotFoundException;
import net.chrisphilbin.cms.repository.PostRepository;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    
    private PostRepository postRepository;
    
    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @Override
    public Post getPost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return unwrapPost(post, id);
    }

    @Override
    public Post savePost(Post post) {
        this.incrementPostCount(post);
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        this.decrementCategoryPostCount(id);
        postRepository.deleteById(id);   
    }

    @Override
    public Post updatePost(Post newPost, Post oldPost) {
        oldPost.setBody(newPost.getBody());
        oldPost.setTitle(newPost.getTitle());
        oldPost.setIs_published(newPost.getIs_published());
        return postRepository.save(oldPost);
    }

    @Override
    public List<Post> getPosts(Long userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public Boolean verifyPostBelongsToUser(Post post, Principal principal) {
        return post.getUser().getId() != userService.getUser(principal.getName()).getId();
    }

    @Override
    public List<Post> getPostsByCategoryId(Long categoryId) {
        return postRepository.findByCategoryId(categoryId);
    }

    static Post unwrapPost(Optional<Post> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, Post.class);
    }

    private void decrementCategoryPostCount(Long postId) {
        Category category = this.getPost(postId).getCategory();
        category.setNumberOfPosts(category.getNumberOfPosts() - 1);
        categoryService.saveCategory(category);
    }

    private void incrementPostCount(Post post) {
        Category category = post.getCategory();
        category.setNumberOfPosts(category.getNumberOfPosts() + 1);
        categoryService.saveCategory(category);
    }
}
