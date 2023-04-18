package com.satisfyyourcuriosity.filipapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.satisfyyourcuriosity.filipapp.domain.Category;
import com.satisfyyourcuriosity.filipapp.domain.CategoryRepository;
import com.satisfyyourcuriosity.filipapp.domain.Quiz;
import com.satisfyyourcuriosity.filipapp.domain.QuizRepository;
import com.satisfyyourcuriosity.filipapp.domain.Topic;
import com.satisfyyourcuriosity.filipapp.domain.TopicRepository;
import com.satisfyyourcuriosity.filipapp.domain.User;
import com.satisfyyourcuriosity.filipapp.domain.UserRepository;

@SpringBootApplication
@RestController
public class FilipAppApplication {

	@Autowired
	private UserRepository userrep;

	@GetMapping("/user")
	public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
		long githubId = ((Number) principal.getAttribute("id")).longValue();
		List<User> userl = userrep.findById(githubId);
		if (userl == null) {
			User user = new User(githubId, principal.getAttribute("login"), 0);
			userrep.save(user);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("name", principal.getAttribute("login"));
		m.put("id", principal.getAttribute("id"));
		return m;
	}

	private static final Logger log = LoggerFactory.getLogger(FilipAppApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FilipAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner bookDemo(TopicRepository topicrep, CategoryRepository categoryrep,
			QuizRepository quizrep) {
		return (args) -> {
			List<Topic> topics = (List<Topic>) topicrep.findAll();
			if (topics.size() == 0) {
				log.info("Categories");
				Category space = new Category("Space");
				Category coding = new Category("Coding");
				Category languages = new Category("Languages");
				categoryrep.save(space);
				categoryrep.save(coding);
				categoryrep.save(languages);
				categoryrep.save(new Category("Miscellaneous"));
				log.info("Quizes");
				Quiz quiz1 = new Quiz("When was Apollo 11 launched? (Finnish date format)",
						"What was the name of the Apollo 11 rocket?",
						"How many hours approximately was Apollo 11 on the moon?", "16.07.1969", "Saturn V", "21");
				Quiz quiz2 = new Quiz("How do you say 'Good Morning' in Japanese?",
						"How do you say 'Excuse me' / 'Sorry' in Japanese?",
						"How do you politely say 'thank you' in Japanese?", "ohayou gozaimasu", "sumimasen",
						"arigatou gozaimasu");
				Quiz quiz3 = new Quiz("Which two languages are MySQL written in?",
						"What is the statement to select all records in the 'Topics' table?",
						"What is the statement to select all records in the 'Schools' table where the City is 'Helsinki'?", "C and C++", "SELECT * FROM Topics;",
						"SELECT * FROM Schools WHERE City = 'Helsinki';");
				quizrep.save(quiz1);
				quizrep.save(quiz2);
				quizrep.save(quiz3);
				log.info("Topics");
				topicrep.save(new Topic("Japanese Travel Vocabulary",
						"https://www.fluentu.com/blog/japanese/japanese-travel-phrases/",
						"https://www.japanesepod101.com/japanese-vocabulary-lists/travel/",
						"https://boutiquejapan.com/essential-japanese-words-phrases-for-travelers-to-japan/",
						"Just remember the romaji words (latin letters)", 15, languages, quiz2));
				topicrep.save(
						new Topic("Apollo 11 Mission", "https://www.nasa.gov/mission_pages/apollo/missions/apollo11.html",
								"https://www.britannica.com/topic/Apollo-11", "https://www.youtube.com/watch?v=nOcDftgR5UQ",
								"", 30, space, quiz1));
				topicrep.save(
						new Topic("MySQL basics", "https://www.mysqltutorial.org/mysql-basics/",
								"https://www.w3schools.com/MySQL/default.asp", "https://www.javatpoint.com/mysql-tutorial",
								"", 20, coding, quiz3));
				// Populating some users
				userrep.save(new User(131431L, "User 1", 3));
				userrep.save(new User(131432L, "User 2", 2));
				userrep.save(new User(131433L, "User 3", 5));
			}
			
			else {
				log.info("Database already populated");
			}
			
		};
	}
}
