package com.codes.tasktracker.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan(basePackages = "com.codes.tasktracker.demo")
public class TaskTrackerApplication {
	public static void main(String[] args) {
		SpringApplication.run(TaskTrackerApplication.class, args);
	}
}

