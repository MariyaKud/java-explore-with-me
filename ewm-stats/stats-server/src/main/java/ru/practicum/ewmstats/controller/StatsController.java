package ru.practicum.ewmstats.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
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
@Validated
@RequestMapping
public class StatsController {
    private final StatsService statsService;

    @GetMapping("/stats")
    public List<ViewStatsDto> getStats(
            @NonNull @RequestParam("start") @DateTimeFormat(pattern = ContextStats.pattern) LocalDateTime startData,
            @NonNull @RequestParam("end") @DateTimeFormat(pattern = ContextStats.pattern) LocalDateTime endData,
            @RequestParam(name = "uris", defaultValue = "") List<String> uris,
            @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        log.info("Get all stats start={}, end={} for list uri={}, unique={}", startData, endData, uris, unique);
        if (endData.isBefore(startData)) {
            throw new IllegalArgumentException("Date start is after then end.");
        }
        return statsService.getStats(startData, endData, uris, unique);
    }

    @PostMapping("/hit")
    @ResponseStatus(value = HttpStatus.CREATED)
    public EndpointHitDto createHit(@RequestBody EndpointHitDto dto) {
        log.info("Creating endpointHit {}", dto);
        return statsService.createHit(dto);
    }
}