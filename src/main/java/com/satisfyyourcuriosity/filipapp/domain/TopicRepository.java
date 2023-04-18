package com.satisfyyourcuriosity.filipapp.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TopicRepository extends CrudRepository<Topic, Long> {
	List<Topic> findById(long topicId);
}
