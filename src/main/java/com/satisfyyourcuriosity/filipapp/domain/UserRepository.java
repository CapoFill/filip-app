package com.satisfyyourcuriosity.filipapp.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
	List<User> findById(long githubId);
	List<User> findAllByOrderByScoreDesc();
}
