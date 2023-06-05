package net.chrisphilbin.cms.repository;
import java.util.Optional;


import org.springframework.data.repository.CrudRepository;

import net.chrisphilbin.cms.entity.Category;


public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByCategoryId(Long categoryId);
}
