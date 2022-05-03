package dmacc.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dmacc.beans.ComicBookInformation;
import dmacc.beans.UserInformation;

/**
 * @author jword - jord
 * CIS175 - Spring - 2022
 * May 2, 2022
 */
@Configuration
public class BeanConfiguration {
	
	@Bean
	public ComicBookInformation comic() {
		ComicBookInformation bean = new ComicBookInformation();
		return bean;
	}
	@Bean
	public UserInformation user() {
	UserInformation bean = new UserInformation();
	return bean;
	
		
	}
}

