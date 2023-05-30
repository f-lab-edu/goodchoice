package com.flab.goodchoice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class GoodchoiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoodchoiceApplication.class, args);
	}

}
