package com.assecor.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*This class responsible start the application.
 * */
@SpringBootApplication(scanBasePackages={"com.assecor"})
@EntityScan("com.assecor.entity")
@EnableJpaRepositories("com.assecor.repository")
public class AssecorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssecorApplication.class, args);
	}
	
}
