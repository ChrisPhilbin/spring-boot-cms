package net.chrisphilbin.cms.repository;
import java.util.Optional;
import java.util.Locale.Category;

import org.springframework.data.repository.CrudRepository;


public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByCategoryId(Long categoryId);
}
