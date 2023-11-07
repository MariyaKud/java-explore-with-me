package ru.practicum.ewmstats.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import ru.practicum.dto.ContextStats;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;

import ru.practicum.ewmstats.service.StatsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping
public class StatsController {
    private final StatsService statsService;

    @GetMapping("/stats")
    public List<ViewStatsDto> getStats(
            @RequestParam("start") @DateTimeFormat(pattern = ContextStats.pattern) LocalDateTime startData,
            @RequestParam("end") @DateTimeFormat(pattern = ContextStats.pattern) LocalDateTime endData,
            @RequestParam(name = "uris", defaultValue = "") List<String> uris,
            @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        log.info("Get all stats start={}, end={} for list uri={}, unique={}", startData, endData, uris, unique);
        return statsService.getStats(startData, endData, uris, unique);
    }

    @PostMapping("/hit")
    public EndpointHitDto createHit(@RequestBody EndpointHitDto dto) {
        log.info("Creating endpointHit {}", dto);
        return statsService.createHit(dto);
    }
}