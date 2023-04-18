package com.satisfyyourcuriosity.filipapp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import com.satisfyyourcuriosity.filipapp.domain.Category;
import com.satisfyyourcuriosity.filipapp.domain.CategoryRepository;
import com.satisfyyourcuriosity.filipapp.domain.Quiz;
import com.satisfyyourcuriosity.filipapp.domain.QuizRepository;
import com.satisfyyourcuriosity.filipapp.domain.Topic;
import com.satisfyyourcuriosity.filipapp.domain.TopicRepository;
import com.satisfyyourcuriosity.filipapp.domain.User;
import com.satisfyyourcuriosity.filipapp.domain.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class RepositoryTest {
	@Autowired
	private CategoryRepository categoryrep;
	@Autowired
	private QuizRepository quizrep;
	@Autowired
	private TopicRepository topicrep;
	@Autowired
	private UserRepository userrep;

	// CategoryRepository tests
	@Test
	public void testFindByCategoryId() {
		List<Category> categories = categoryrep.findById(1L);
		assertThat(categories).hasSize(1);
	}

	@Test
	public void testCreateCategory() {
		Category test = new Category("Test");
		categoryrep.save(test);
		assertThat(test.getId()).isNotNull();
	}

	// QuizRepository test

	@Test
	public void testFindByQuizId() {
		List<Quiz> quizzes = quizrep.findById(1L);
		assertThat(quizzes.get(0).getQ1().equals("When was Apollo 11 launched? (Finnish date format)"));
	}

	@Test
	public void testCreateQuizAndEqualsIgnoreCase() {
		Quiz testQuiz = new Quiz("What is 3+3", "What is the Finnish name for 'bird'?",
				"Is Sweden the capital of Finland?", "6", "lintu", "No");
		quizrep.save(testQuiz);
		assertThat(testQuiz.getA2().equalsIgnoreCase("LInTu"));
	}

	// TopicRepository test

	@Test
	public void testFindTopic() {
		List<Topic> topics = topicrep.findById(1L);
		assertThat(topics.get(0).getTitle().equals("Japanese Travel Vocabulary"));
	}

	@Test
	public void testCreateTopic() {
		Topic testTopic = new Topic("Title", "URL1", "URL2", "URL3", "Hints", 3000, new Category("Test"),
				new Quiz("Q1", "Q2", "Q3", "A1", "A2", "A3"));
		assertThat(testTopic.getTopicId()).isNotNull();
	}

	// UserRepository test

	@Test
	public void testFindUser() {
		List<User> users = userrep.findById(131431L);
		assertThat(users.get(0).getScore() == 3);
	}

	@Test
	public void testCreateUser() {
		User testUser = new User(1234567L, "Nickname", 541);
		assertThat(testUser.getCompletedTopics().size() == 0);
	}
}
