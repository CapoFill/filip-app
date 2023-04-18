package com.satisfyyourcuriosity.filipapp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.satisfyyourcuriosity.filipapp.web.AppController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AppControllerTest {
	@Autowired
	private AppController controller;

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
}
