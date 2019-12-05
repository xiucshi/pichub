package com.cong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class PichubApplication {

	public static void main(String[] args) {
		SpringApplication.run(PichubApplication.class, args);
	}

}
