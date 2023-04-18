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
public class Quiz {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long quizId;
	private String q1;
	private String q2;
	private String q3;

	private String a1;
	private String a2;
	private String a3;
	
	public Quiz() {}
	
	public Quiz(String q1, String q2, String q3, String a1, String a2, String a3) {
		super();
		this.q1 = q1;
		this.q2 = q2;
		this.q3 = q3;
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
	}

	public long getQuizId() {
		return quizId;
	}

	public void setQuizId(long quizId) {
		this.quizId = quizId;
	}

	public String getQ1() {
		return q1;
	}

	public void setQ1(String q1) {
		this.q1 = q1;
	}

	public String getQ2() {
		return q2;
	}

	public void setQ2(String q2) {
		this.q2 = q2;
	}

	public String getQ3() {
		return q3;
	}

	public void setQ3(String q3) {
		this.q3 = q3;
	}

	public String getA1() {
		return a1;
	}

	public void setA1(String a1) {
		this.a1 = a1;
	}

	public String getA2() {
		return a2;
	}

	public void setA2(String a2) {
		this.a2 = a2;
	}

	public String getA3() {
		return a3;
	}

	public void setA3(String a3) {
		this.a3 = a3;
	}

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "quiz")
	private List<Topic> topics;
}
