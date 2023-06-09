package net.chrisphilbin.cms.web;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.chrisphilbin.cms.entity.Category;
import net.chrisphilbin.cms.entity.Post;
import net.chrisphilbin.cms.exception.ErrorResponse;
import net.chrisphilbin.cms.repository.CategoryRepository;
import net.chrisphilbin.cms.service.CategoryService;
import net.chrisphilbin.cms.service.PostService;
import net.chrisphilbin.cms.service.UserService;


@AllArgsConstructor
@RestController
@Tag(name = "post Controller", description = "Create, retrieve, and edit post items")
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;
    UserService userService;
    CategoryService categoryService;
    CategoryRepository categoryRepository;

    @Operation(summary = "Create post item", description = "Creates a post item from the provided payload")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successful creation of post item"),
        @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
     })
    @PostMapping
    public ResponseEntity<Post> savePost(@Valid @RequestBody Post post, @RequestParam String categoryId, Principal principal) {
        Category category = categoryService.getCategory(Long.parseLong(categoryId));
        post.setCategory(category);
        post.setUser(userService.getUser(principal.getName()));
        return new ResponseEntity<>(postService.savePost(post), HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieves single post item", description = "Returns a single post item by given ID")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Successful retrieval of post item", content = @Content(schema = @Schema(implementation = Post.class))),
        @ApiResponse(responseCode = "404", description = "post item with specified ID doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id, Principal principal) {
        return new ResponseEntity<Post>(postService.getPost(id), HttpStatus.OK);
    }

    @Operation(summary = "Retrieves all post items", description = "Returns a list of all post items")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of all post items", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Post.class))))
    @GetMapping("/all")
    public ResponseEntity<List<Post>> getPosts(Principal principal) {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @Operation(summary = "Delete a post by ID", description = "Deletes specified post item by provided ID")
    @DeleteMapping("/{postId}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable Long postId, Principal principal) {
        if (postService.verifyPostBelongsToUser(postService.getPost(postId), principal)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update a post by ID", description = "Updates specified post item by provided ID")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Successful retrieval of post item", content = @Content(schema = @Schema(implementation = Post.class))),
        @ApiResponse(responseCode = "404", description = "post item with specified ID doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatepost(@PathVariable Long postId, @RequestBody Post post, Principal principal) {
        Post oldPost = postService.getPost(postId);
        if (postService.verifyPostBelongsToUser(oldPost, principal)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<Post>(postService.updatePost(post, oldPost), HttpStatus.OK);
    }
    
}
