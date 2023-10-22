package ru.practicum.ewmstats.model;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import ru.practicum.dto.EndpointHitDto;

import static ru.practicum.ewmstats.model.ContextStats.formatter;

@Component
public class HitMapper {

    public EndpointHitDto toDto(EndpointHit endpointHit) {
        return new EndpointHitDto(endpointHit.getId(), endpointHit.getApp(), endpointHit.getUri(),
                                   endpointHit.getIp(), formatter.format(endpointHit.getTimestamp()));
    }

    public EndpointHit fromDto(EndpointHitDto endpointHit) {
        return new EndpointHit(endpointHit.getId(), endpointHit.getApp(), endpointHit.getUri(),
                                endpointHit.getIp(), LocalDateTime.parse(endpointHit.getTimestamp(), formatter));
    }
}
