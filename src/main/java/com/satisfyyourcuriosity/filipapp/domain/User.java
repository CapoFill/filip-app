package com.satisfyyourcuriosity.filipapp.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"user\"")
public class User {
	@Id
	private long githubId;
	private String nick;
	private int score;
	// Would have loved for this to reference the Topic instances directly, but I kept on getting vague JdbcType errors so I just stored topicId 
	private final List<Long> completedTopics = new ArrayList<>();

	public User() {
	}

	public User(long githubId, String nick, int score) {
		super();
		this.githubId = githubId;
		this.nick = nick;
		this.score = score;
	}

	public long getGithubId() {
		return githubId;
	}

	public void setGithubId(long githubId) {
		this.githubId = githubId;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public int getScore() {
		return score;
	}

	public void addScore(int addScore) {
		score += addScore;
	}

	public List<Long> getCompletedTopics() {
		return completedTopics;
	}

	public void addCompletedTopic(long topicId) {
		completedTopics.add(topicId);
	}
}
