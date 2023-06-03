package net.chrisphilbin.cms.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import net.chrisphilbin.cms.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByUsername(String username);
}