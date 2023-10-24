package ru.practicum.ewmstats.repository;

import ru.practicum.dto.ViewStatsDto;
import ru.practicum.ewmstats.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitRepository {

    EndpointHit save(EndpointHit e);

    List<ViewStatsDto> getStats(LocalDateTime startData, LocalDateTime endData, List<String> uris, Boolean unique);
}
