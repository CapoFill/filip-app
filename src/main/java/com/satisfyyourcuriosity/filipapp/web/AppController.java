package com.satisfyyourcuriosity.filipapp.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.satisfyyourcuriosity.filipapp.domain.Category;
import com.satisfyyourcuriosity.filipapp.domain.CategoryRepository;
import com.satisfyyourcuriosity.filipapp.domain.Quiz;
import com.satisfyyourcuriosity.filipapp.domain.QuizRepository;
import com.satisfyyourcuriosity.filipapp.domain.Topic;
import com.satisfyyourcuriosity.filipapp.domain.TopicRepository;
import com.satisfyyourcuriosity.filipapp.domain.User;
import com.satisfyyourcuriosity.filipapp.domain.UserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {
	@Autowired
	private QuizRepository quizrep;
	@Autowired
	private CategoryRepository categoryrep;
	@Autowired
	private TopicRepository topicrep;
	@Autowired
	private UserRepository userrep;

	@GetMapping("/")
	public String home(Model model) {
		return "index";
	}

	@GetMapping("/index")
	public String index(Model model) {
		return "index";
	}

	@GetMapping("/topic/{topicId}")
	public String topics(@PathVariable("topicId") Long topicId, @AuthenticationPrincipal OAuth2User principal, Model model) {
		List<User> userl = userrep.findById(((Number) principal.getAttribute("id")).longValue());
		User user = userl.get(0);
		boolean found = false;
		for (long topic : user.getCompletedTopics()) {
			if (topicId == topic)  {
				found = true;
			}
		}
		
		if (found) {
			return "topicAlreadyCompleted";
		}
		model.addAttribute("topic", topicrep.findById(topicId).get());
		return "topic";
	}

	@GetMapping("/leaderboard")
	public String leaderboard(Model model) {
		model.addAttribute("users", userrep.findAllByOrderByScoreDesc());
		return "leaderboard";
	}

	@RequestMapping(value = "/add")
	public String addTopic(Model model) {
		model.addAttribute("topic", new Topic());
		model.addAttribute("quiz", new Quiz());
		model.addAttribute("categories", categoryrep.findAll());
		return "addTopic";
	}
	
	@RequestMapping(value = "/addCategory")
	public String addCategory(Category category, Model model) {
		model.addAttribute("category", new Category());
		return "addCategory";
	}

	@RequestMapping(value = "/saveCategory", method = RequestMethod.POST)
	public String saveCategory(Category category) {
		categoryrep.save(category);
		return "redirect:add";
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Topic topic, Quiz quiz) {
		quizrep.save(quiz);
		topic.setQuiz(quiz);
		topicrep.save(topic);
		return "redirect:index";
	}

	@RequestMapping(value = "/quiz/{topicId}")
	public String quizServe(@PathVariable("topicId") Long topicId, Model model) {
		model.addAttribute("topic", topicrep.findById(topicId).get());
		return "quiz";
	}
	
	@RequestMapping(value = "/quizCheck")
	public String quizCheck(@AuthenticationPrincipal OAuth2User principal, @RequestParam(value = "answer1", required = true) String answer1,
			@RequestParam(value = "answer2", required = true) String answer2,
			@RequestParam(value = "answer3", required = true) String answer3,
			@RequestParam(value = "topicId", required = true) long topicId, Model model) {
		List<Topic> topicl = topicrep.findById(topicId);
		Topic topic = topicl.get(0);
		List<User> userl = userrep.findById(((Number) principal.getAttribute("id")).longValue());
		User user = userl.get(0);
		int points = 0;
		boolean a1correct = false;
		boolean a2correct = false;
		boolean a3correct = false;
		if (answer1.equalsIgnoreCase(topic.getQuiz().getA1())) {
			points += 1;
			a1correct = true;
		}
		if (answer2.equalsIgnoreCase(topic.getQuiz().getA2())) {
			points += 2;
			a2correct = true;
		}
		if (answer3.equalsIgnoreCase(topic.getQuiz().getA3())) {
			points += 3;
			a3correct = true;
		}
		user.addScore(points);
		user.addCompletedTopic(topicId);
		userrep.save(user);
		model.addAttribute("topic", topic);
		model.addAttribute("points", points);
		model.addAttribute("a1correct", a1correct);
		model.addAttribute("a2correct", a2correct);
		model.addAttribute("a3correct", a3correct);
		if (!a1correct) {
			model.addAttribute("answer1", answer1);
		}
		if (!a2correct) {
			model.addAttribute("answer2", answer2);
		}
		if (!a3correct) {
			model.addAttribute("answer3", answer3);
		}
		return "quizResults";
	}

	@RequestMapping(value = "/getThreeTopics")
	public String getThreeTopics(@AuthenticationPrincipal OAuth2User principal, Model model) {
		Random rand = new Random();
		List<Topic> topics = (List<Topic>) topicrep.findAll();
		List<User> userl = userrep.findById(((Number) principal.getAttribute("id")).longValue());
		User user = userl.get(0);
		// Making a list of all topics the user has completed, with Topic reference
		// instead of id reference
		List<Topic> completedTopics = new ArrayList<Topic>();
		for (long topicId : user.getCompletedTopics()) {
			completedTopics.add(topicrep.findById(topicId).get(0));
		}
		// Finding topics that the user has not yet completed, and serving them to the
		// user
		List<Topic> validTopics = new ArrayList<Topic>();
		List<Topic> selectedTopics = new ArrayList<Topic>();
		boolean found = false;
		for (Topic topic : topics) {
			found = false;
			for (Topic completedTopic : completedTopics) {
				if (topic.getTopicId() == completedTopic.getTopicId()) {
					found = true;
				}
			}
			if (!found) {
				validTopics.add(topic);
			}
		}

		int topicCount = validTopics.size();
		int topic1 = -1;
		int topic2 = -1;
		int topic3 = -1;
		if (topicCount > 0) {
			topic1 = rand.nextInt(topicCount);
			selectedTopics.add(validTopics.get(topic1));
		}
		if (topicCount > 1) {
			while (topic2 == topic1 || topic2 == -1) {
				topic2 = rand.nextInt(topicCount);
			}
			selectedTopics.add(validTopics.get(topic2));
		}
		if (topicCount > 2) {
			while (topic3 == topic1 || topic3 == topic2 || topic3 == -1) {
				topic3 = rand.nextInt(topicCount);

			}
			selectedTopics.add(validTopics.get(topic3));
		}

		model.addAttribute("topics", selectedTopics);
		return "index";
	}
}
