package com.annotation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.annotation.mapper")
public class AnnotationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnnotationApplication.class, args);
	}

}
