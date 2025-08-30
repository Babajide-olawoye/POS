package com.AAA.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the POS Spring Boot application.
 * <p>
 * The {@link PosApplication} class simply boots the Spring context.
 */
@SpringBootApplication
public class PosApplication {

        /**
         * Bootstraps the application using Spring Boot's {@link SpringApplication}.
         *
         * @param args command line arguments
         */
        public static void main(String[] args) {
                SpringApplication.run(PosApplication.class, args);
        }

}
