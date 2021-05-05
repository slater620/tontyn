package com.tontyn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class BasicApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(BasicApplication.class, args);
	}

}
