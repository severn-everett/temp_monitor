package com.severett.tempmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.severett")
public class TempMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TempMonitorApplication.class, args);
	}
}
