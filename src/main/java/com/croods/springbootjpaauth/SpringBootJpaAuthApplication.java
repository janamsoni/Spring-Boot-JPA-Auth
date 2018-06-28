package com.croods.springbootjpaauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringBootJpaAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaAuthApplication.class, args);
		
		String password = "uv";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		System.out.println("===========================================");
		System.out.println(hashedPassword);
		System.out.println("===========================================");

	}
}
