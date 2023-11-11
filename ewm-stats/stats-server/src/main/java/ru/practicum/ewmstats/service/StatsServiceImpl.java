package ru.practicum.ewmstats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.ewmstats.model.HitMapper;
import ru.practicum.ewmstats.model.EndpointHit;
import ru.practicum.ewmstats.repository.EndpointHitRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final EndpointHitRepository endpointHitRepository;

    private final HitMapper hitMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ViewStatsDto> getStats(LocalDateTime startData, LocalDateTime endData,
                                        List<String> uris, Boolean unique) {
        return endpointHitRepository.getStats(startData, endData, uris, unique);
    }

    @Override
    @Transactional
    public EndpointHitDto createHit(EndpointHitDto endpointHitDto) {
        EndpointHit hit = hitMapper.fromDto(endpointHitDto);
        EndpointHit endpointHit = endpointHitRepository.save(hit);
        return hitMapper.toDto(endpointHit);
    }
}
