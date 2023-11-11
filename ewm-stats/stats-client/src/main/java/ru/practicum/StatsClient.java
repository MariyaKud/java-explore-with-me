package ru.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import java.util.List;

import static ru.practicum.dto.ContextStats.formatter;

@Service
public class StatsClient extends BaseClient {
    private static final String API_PREFIX = "";

    @Autowired
    public StatsClient(@Value("${serverUrl.path}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    @Transactional
    public ResponseEntity<Object> createHit(EndpointHitDto hitDto) {
        return post("/hit", hitDto);
    }

    public ResponseEntity<List<ViewStatsDto>> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {

        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("/stats?start=").append(formatter.format(start));
        uriBuilder.append("&end=").append(formatter.format(end));
        for (String s : uris) {
            uriBuilder.append("&uris=").append(s);
        }
        uriBuilder.append("&unique=").append(unique);

        return get(uriBuilder.toString());
    }
}