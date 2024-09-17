package edu.depaul.cdm.se452.rfa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@Log4j2
public class RoommateFinderApp {

	public static void main(String[] args) {
		SpringApplication.run(RoommateFinderApp.class, args);
	}

}
