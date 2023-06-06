package net.chrisphilbin.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.chrisphilbin.cms.entity.Category;
import net.chrisphilbin.cms.exception.EntityNotFoundException;
import net.chrisphilbin.cms.repository.CategoryRepository;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Override
    public Category getCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return unwrapCategory(category, id);
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category newCategory, Category oldCategory) {
        oldCategory.setCat_name(newCategory.getCat_name());
        return categoryRepository.save(oldCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        Iterable<Category> categoryItr = categoryRepository.findAll();
        List<Category> allCategories = new ArrayList<Category>();
        categoryItr.forEach(allCategories::add);
        return allCategories;
    }

    static Category unwrapCategory(Optional<Category> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, Category.class);
    }
    
}
