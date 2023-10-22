package ru.practicum.ewmstats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.ewmstats.model.HitMapper;
import ru.practicum.ewmstats.model.EndpointHit;
import ru.practicum.ewmstats.repository.EndpointHitRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final EndpointHitRepository endpointHitRepository;

    private final HitMapper hitMapper;

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime startData, LocalDateTime endData, String uris, Boolean unique) {
        List<String> urisParam = new ArrayList<>();
        if (!uris.isBlank()) {
            String[] parts = uris.split(",");
            Collections.addAll(urisParam, parts);
        }
        return endpointHitRepository.getStats(startData, endData, urisParam, unique);
    }

    @Override
    public EndpointHitDto createHit(EndpointHitDto endpointHitDto) {
        EndpointHit hit = hitMapper.fromDto(endpointHitDto);
        EndpointHit endpointHit = endpointHitRepository.save(hit);
        return hitMapper.toDto(endpointHit);
    }
}
