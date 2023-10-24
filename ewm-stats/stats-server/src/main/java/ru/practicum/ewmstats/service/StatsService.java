package ru.practicum.ewmstats.service;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    List<ViewStatsDto> getStats(LocalDateTime startData, LocalDateTime endData, List<String> uris, Boolean unique);

    EndpointHitDto createHit(EndpointHitDto endpointHitDto);
}
