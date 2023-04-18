package com.satisfyyourcuriosity.filipapp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Topic {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long topicId;
	private String title;
	// URLs for learning
	private String url1;
	private String url2;
	private String url3;
	private String userHints;
	private int timeExpected;
	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;
	@ManyToOne
	@JoinColumn(name = "quizId")
	private Quiz quiz;

	@Override
	public String toString() {
		return "Topic [topicId=" + topicId + ", title=" + title + ", url1=" + url1 + ", url2=" + url2 + ", url3=" + url3
				+ ", userHints=" + userHints + ", timeExpected=" + timeExpected + ", category=" + category + ", quiz="
				+ quiz + "]";
	}
	public Topic() {}
	public Topic(String title, String url1, String url2, String url3, String userHints, int timeExpected, Category category, Quiz quiz) {
		super();
		this.title = title;
		this.url1 = url1;
		this.url2 = url2;
		this.url3 = url3;
		this.userHints = userHints;
		this.timeExpected = timeExpected;
		this.category = category;
		this.quiz = quiz;
	}

	public long getTopicId() {
		return topicId;
	}
	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl1() {
		return url1;
	}
	public void setUrl1(String url1) {
		this.url1 = url1;
	}
	public String getUrl2() {
		return url2;
	}
	public void setUrl2(String url2) {
		this.url2 = url2;
	}
	public String getUrl3() {
		return url3;
	}
	public void setUrl3(String url3) {
		this.url3 = url3;
	}
	public String getUserHints() {
		return userHints;
	}
	public void setUserHints(String userHints) {
		this.userHints = userHints;
	}
	public int getTimeExpected() {
		return timeExpected;
	}
	public void setTimeExpected(int timeExpected) {
		this.timeExpected = timeExpected;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Quiz getQuiz() {
		return quiz;
	}
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}
}
