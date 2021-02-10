package com.cts.recruitment.assignment.javaexercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;

@SpringBootApplication(exclude = {WebMvcAutoConfiguration.class})
public class JavaExerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaExerciseApplication.class, args);
	}

}
