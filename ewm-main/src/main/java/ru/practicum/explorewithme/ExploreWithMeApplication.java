package ru.practicum.explorewithme;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ru.practicum.StatsClient;
import ru.practicum.dto.EndpointHitDto;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class ExploreWithMeApplication {

	public static void main(String[] args) {

		/*
		System.out.println("-----------------------");
		System.out.println("START TEST Stats client");

		ResponseEntity<Object> result;
		ObjectMapper mapper = new ObjectMapper();

		RestTemplateBuilder builder = new RestTemplateBuilder();
		StatsClient statsClient = new StatsClient("http://localhost:9090", builder);

		EndpointHitDto dto = new EndpointHitDto(1L, "ewm-main-service", "/events/1",
				                               "192.163.0.1", "2023-09-06 11:00:23");

		result = statsClient.createHit(dto);
		System.out.println("Test endpoint /hit");
		System.out.println("Stats client return " + result.getStatusCode());
		if (result.getStatusCode() == HttpStatus.OK) {
			System.out.println("Stats client create EndpointHit:");
			try {
				System.out.println(mapper.writeValueAsString(result.getBody()));
			} catch (JsonProcessingException e) {
				System.out.println("We have a problem..");
            }
        }

		result = statsClient.getStats(LocalDateTime.of(2023, 9, 6, 0, 0, 0),
			                     	   LocalDateTime.of(2023, 9, 7, 0, 0, 0),
				                         List.of("/events/1"), false);

		System.out.println("Test endpoint /stats");
		System.out.println("Stats client return " + result.getStatusCode());
		 */

		SpringApplication.run(ExploreWithMeApplication.class, args);
	}
}
