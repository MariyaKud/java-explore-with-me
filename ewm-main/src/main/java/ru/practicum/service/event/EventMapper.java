package ru.practicum.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.dto.input.create.NewEventDto;
import ru.practicum.dto.output.CategoryDto;
import ru.practicum.dto.output.EventFullDto;
import ru.practicum.dto.output.outshort.EventShortDto;
import ru.practicum.dto.output.outshort.LocationDto;
import ru.practicum.dto.output.outshort.UserShortDto;
import ru.practicum.model.Event;

import java.util.*;

import static ru.practicum.dto.ContextStats.formatter;

@Component
@RequiredArgsConstructor
public class EventMapper {
    public Event fromDto(NewEventDto newEventDto) {
        return Event.builder()
                .title(newEventDto.getTitle())
                .description(newEventDto.getDescription())
                .annotation(newEventDto.getAnnotation())
                .paid(newEventDto.getPaid())
                .eventDate(newEventDto.getEventDate())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .build();
    }

    public EventFullDto toFullDto(Event event, Integer views, Integer confirmedRequests) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(new CategoryDto(event.getCategory().getId(), event.getCategory().getName()))
                .createdOn(formatter.format(event.getCreatedOn()))
                .description(event.getDescription())
                .eventDate(formatter.format(event.getEventDate()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.getRequestModeration())
                .title(event.getTitle())
                .initiator(new UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()))
                .state(event.getState())
                .location(new LocationDto(event.getLocation().getLat(), event.getLocation().getLon()))
                .views(views)
                .confirmedRequests(confirmedRequests)
                .build();
    }

    public EventShortDto toShortDto(Event event, Integer views, Integer confirmedRequests) {
        return EventShortDto.builder()
                .id(event.getId())
                .description(event.getDescription())
                .category(new CategoryDto(event.getCategory().getId(), event.getCategory().getName()))
                .annotation(event.getAnnotation())
                .eventDate(formatter.format(event.getEventDate()))
                .initiator(new UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(views)
                .confirmedRequests(confirmedRequests)
                .build();
    }

    public List<EventShortDto> mapToEventDto(Iterable<Event> events, Map<Long, Integer> viewsEvents,
                                            Map<Long, Integer> confirmRequests, boolean isOnlyAvailable) {
        List<EventShortDto> target = new ArrayList<>();
        events.forEach(u -> {
            EventShortDto eventShortDto = toShortDto(u, viewsEvents.getOrDefault(u.getId(), 0),
                                                      confirmRequests.getOrDefault(u.getId(), 0));
            if (!isOnlyAvailable || u.getParticipantLimit() == 0 || !u.getRequestModeration() ||
                    eventShortDto.getConfirmedRequests() < u.getParticipantLimit()) {
                target.add(eventShortDto);
            }
        });

        return target;
    }

    public List<EventFullDto> mapToFullEventDto(Iterable<Event> events, Map<Long, Integer> viewsEvents,
                                                Map<Long, Integer> confirmRequests) {
        List<EventFullDto> target = new ArrayList<>();
        events.forEach(u -> target.add(toFullDto(u, viewsEvents.getOrDefault(u.getId(), 0),
                confirmRequests.getOrDefault(u.getId(), 0))));
        return target;
    }
}
