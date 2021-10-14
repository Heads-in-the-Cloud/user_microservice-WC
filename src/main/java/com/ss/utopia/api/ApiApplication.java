package com.ss.utopia.api;


import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@SpringBootApplication
@EnableJpaRepositories()
public class ApiApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
		System.out.println(new BCryptPasswordEncoder().encode("password"));

		

		
	}

}
