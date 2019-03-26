package com.nyfz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@MapperScan("com.nyfz.mapper")
@ComponentScan
@EnableAutoConfiguration
@Configuration
public class AppRun {

	public static void main(String[] args) {
		SpringApplication.run(AppRun.class, args);

	}

}
