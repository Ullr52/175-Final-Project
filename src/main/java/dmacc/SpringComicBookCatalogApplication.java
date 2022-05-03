package dmacc;

import org.springframework.boot.SpringApplication;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import dmacc.beans.ComicBookInformation;
import dmacc.beans.UserInformation;
import dmacc.controller.BeanConfiguration;

@SpringBootApplication
public class SpringComicBookCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringComicBookCatalogApplication.class, args);
		
		ApplicationContext appContext = new AnnotationConfigApplicationContext(BeanConfiguration.class);
		
		ComicBookInformation c = appContext.getBean("comic", ComicBookInformation.class);
		UserInformation u = appContext.getBean("user", UserInformation.class);
		
		System.out.println(c.toString());
		System.out.println(u.toString());
	}

}
