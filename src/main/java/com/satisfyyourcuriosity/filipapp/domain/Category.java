package com.satisfyyourcuriosity.filipapp.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long categoryId;
	private String name;
	
	public Category() {}
	
	public Category(String name) {
		super();
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return categoryId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
	private List<Topic> topics;

	/**
	 * @return the books
	 */
	public List<Topic> getTopics() {
		return topics;
	}


	@Override
	public String toString() {
		return name;
	}
}
