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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.chrisphilbin.cms.entity.Category;
import net.chrisphilbin.cms.entity.Post;

@AllArgsConstructor
@RestController
@Tag(name ="category controller", description = "Create, read, update, and delete category items.")
@RequestMapping("/category")
public class CategoryController {
    
    @PostMapping
    public ResponseEntity<Category> saveCategory(@Valid @RequestBody Category category, Principal principal) {

    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {

    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryDetails(@PathVariable Long id, Principal principal) {

    }

    @GetMapping("/{id}/all")
    public ResponseEntity<List<Post>> getAllPostsInCategory(@PathVariable Long id, Principal principal) {

    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category, Principal principal) {

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long id, Principal principal) {

    }

}
