package net.chrisphilbin.cms.service;

import java.util.List;
import net.chrisphilbin.cms.entity.Category;

public interface CategoryService {
    Category getCategory(Long id);
    Category saveCategory(Category category);
    Category updateCategory(Category newCategory, Category oldCategory);
    void deleteCategory(Long id);
    List<Category> getAllCategories();
}
