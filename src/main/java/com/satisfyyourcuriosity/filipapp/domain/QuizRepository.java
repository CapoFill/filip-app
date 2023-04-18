package com.satisfyyourcuriosity.filipapp.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface QuizRepository extends CrudRepository<Quiz, Long>{
	List<Quiz> findById(long id);
}
