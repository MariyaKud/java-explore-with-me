package ru.practicum.explorewithme.service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.StatsClient;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.explorewithme.dto.ContextMain;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.practicum.dto.ContextStats.formatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsAgentImpl implements StatsAgent {

    ObjectMapper mapper = new ObjectMapper();
    private final RestTemplateBuilder builder = new RestTemplateBuilder();
    private final StatsClient statsClient = new StatsClient(ContextMain.uriStats, builder);

    @Override
    public void recordStats(String ip, String uri) {
        EndpointHitDto hitDto = EndpointHitDto.builder()
                .app(ContextMain.app)
                .ip(ip)
                .uri(uri)
                .timestamp(formatter.format(LocalDateTime.now()))
                .build();

        ResponseEntity<Object> result = statsClient.createHit(hitDto);
        log.info("Result of a request {} to increase statistics with an endpoint {}",
                result.getStatusCode(), uri);

        if (result.getStatusCode() == HttpStatus.OK) {
            log.info("Stats client create EndpointHit:");
            try {
                log.info(mapper.writeValueAsString(result.getBody()));
            } catch (JsonProcessingException e) {
                System.out.println("We have a problem to record stats..");
            }
        }
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public int getStatsByEventId(Long eventId, LocalDateTime start, LocalDateTime end) {
        final String path = String.join("/", "/events", Long.toString(eventId));

        final ResponseEntity<List<ViewStatsDto>> stats = statsClient.getStats(start, end, List.of(path), true);

        if (stats.getStatusCode() == HttpStatus.OK && stats.hasBody()) {
            List<ViewStatsDto> viewStatsDtos = stats.getBody();
            if (viewStatsDtos != null && !viewStatsDtos.isEmpty()) {
                return viewStatsDtos.get(0).getHits();
            }
        }

        return 0;
    }

    @Override
    public Map<Long, Integer> getStatsByEventIds(List<Long> ids, LocalDateTime start, LocalDateTime end) {
        Map<Long, Integer> stats = new HashMap<>();
        List<String> paths = ids.stream()
                .map(u -> String.join("/", "/events", Long.toString(u)))
                .collect(Collectors.toList());

        final ResponseEntity<List<ViewStatsDto>> result = statsClient.getStats(start, end, paths, true);

        if (result.getStatusCode() == HttpStatus.OK && result.hasBody()) {
            List<ViewStatsDto> viewStatsDtos = result.getBody();
            if (viewStatsDtos != null && !viewStatsDtos.isEmpty()) {
                for (ViewStatsDto viewStatsDto : viewStatsDtos) {
                    Long eventId = Long.parseLong(viewStatsDto.getUri().substring(8));
                    stats.put(eventId, viewStatsDto.getHits());
                }
            }
        }

        return stats;
    }
}
