package ru.practicum;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;

import java.util.ArrayList;
import java.util.List;


public class BaseClient {
    protected final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected ResponseEntity<List<ViewStatsDto>> get(String path) {
        HttpEntity<?> requestEntity = new HttpEntity<>(defaultHeaders());

        ResponseEntity<List<ViewStatsDto>> statsServerResponse;
        try {
            statsServerResponse = rest.exchange(path, HttpMethod.GET, requestEntity,
                    new ParameterizedTypeReference<>() {
                    });

        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ArrayList<>());
        }

        return statsServerResponse;
    }

    protected ResponseEntity<Object> post(EndpointHitDto body) {
        HttpEntity<EndpointHitDto> requestEntity = new HttpEntity<>(body, defaultHeaders());

        ResponseEntity<Object> statsServerResponse;
        try {
            statsServerResponse = rest.exchange("/hit", HttpMethod.POST, requestEntity, Object.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }

        return prepareGatewayResponse(statsServerResponse);
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        return headers;
    }

    private static ResponseEntity<Object> prepareGatewayResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }
}
