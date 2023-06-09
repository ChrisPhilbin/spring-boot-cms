package net.chrisphilbin.cms.repository;

import org.springframework.data.repository.CrudRepository;

import net.chrisphilbin.cms.entity.Category;


public interface CategoryRepository extends CrudRepository<Category, Long> {
}
