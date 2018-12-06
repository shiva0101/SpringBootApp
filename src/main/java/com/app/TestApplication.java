package com.app;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * This class simply helps us to start the application
 * 
 * @author Shiva Narune
 * @version 1.0
 * @since 23-10-2018
 */

@SpringBootApplication(scanBasePackages = "com")
@EntityScan("com.app.model")
@EnableJpaRepositories("com.app.userRepository")
public class TestApplication extends SpringBootServletInitializer {

	/**
	 * Getting the logger object
	 */

	final static Logger logger = Logger.getLogger(TestApplication.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TestApplication.class);
	}

	public static void main(String[] args) {

		BasicConfigurator.configure();
		SpringApplication.run(TestApplication.class, args);

		logger.info("Application is Started");
		logger.trace("This is a Trace");
		logger.debug("This is a Debug");
		logger.info("This is an Info");
		logger.warn("This is a Warn");
		logger.error("This is an Error");
		logger.info("Tomcat is Started on port : 8888");

	}

}