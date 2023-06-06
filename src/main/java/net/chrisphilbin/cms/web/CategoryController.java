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

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
// import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.chrisphilbin.cms.entity.Category;
import net.chrisphilbin.cms.entity.Post;
import net.chrisphilbin.cms.exception.ErrorResponse;
import net.chrisphilbin.cms.service.CategoryService;
import net.chrisphilbin.cms.service.PostService;

@AllArgsConstructor
@RestController
@Tag(name ="category controller", description = "Create, read, update, and delete category items.")
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    PostService postService;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successful creation of category item"),
        @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
     })
    @Operation(summary = "Create category item", description = "Creates a category from the provided payload")
    @PostMapping("/create")
    public ResponseEntity<Category> saveCategory(@Valid @RequestBody Category category, Principal principal) {
        System.out.println(category.getCat_name());
        System.out.println("category name");
        return new ResponseEntity<>(categoryService.saveCategory(category), HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieves all categories", description = "Returns a list of all catergories")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of all category items", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Category.class))))
    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryDetails(@PathVariable Long id, Principal principal) {
        return new ResponseEntity<>(categoryService.getCategory(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/all")
    public ResponseEntity<List<Post>> getAllPostsInCategory(@PathVariable Long id, Principal principal) {
        return new ResponseEntity<>(postService.getPostsByCategoryId(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category, Principal principal) {
        Category oldCategory = categoryService.getCategory(id);
        return new ResponseEntity<>(categoryService.updateCategory(category, oldCategory), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long id, Principal principal) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
