package ru.practicum.explorewithme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ExploreWithMeApplication {

	public static void main(String[] args) throws InterruptedException {
		TimeUnit.SECONDS.sleep(10);
		SpringApplication.run(ExploreWithMeApplication.class, args);
	}
}
