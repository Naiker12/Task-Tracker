package com.codes.tasktracker.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main application class.
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages = "com.codes.tasktracker.demo")
public final class TaskTrackerApplication {

    private TaskTrackerApplication() {
        // Private constructor to hide the implicit public one
    }

    /**
     * Main method.
     *
     * @param args The command line arguments.
     */
    public static void main(final String[] args) {
        SpringApplication.run(TaskTrackerApplication.class, args);
    }
}
