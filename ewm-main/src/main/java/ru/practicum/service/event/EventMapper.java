package ru.practicum.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.practicum.client.StatsAgent;
import ru.practicum.dto.input.create.NewEventDto;
import ru.practicum.dto.output.AdminCommentDto;
import ru.practicum.dto.output.CategoryDto;
import ru.practicum.dto.output.EventFullDto;
import ru.practicum.dto.output.outshort.EventShortDto;
import ru.practicum.dto.output.outshort.LocationDto;
import ru.practicum.dto.output.outshort.UserShortDto;
import ru.practicum.model.Event;
import ru.practicum.model.QRequest;
import ru.practicum.model.enummodel.RequestStatus;
import ru.practicum.repository.RequestRepository;

import java.time.LocalDateTime;
import java.util.*;

import static ru.practicum.dto.ContextStats.formatter;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private final StatsAgent statsAgent;

    private final RequestRepository requestRepository;

    private final AdminCommentMapper commentMapper;

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
        List<AdminCommentDto> comments = new LinkedList<>();
        if (event.getAdminComments() != null) {
            comments = commentMapper.mapToDto(event.getAdminComments());
        }

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
                .adminComments(comments)
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

    public List<EventShortDto> mapToEventDto(Iterable<Event> events, boolean isOnlyAvailable,
                                             boolean isNeedModeration, List<Long> eventIdsNeedModeration) {
        List<EventShortDto> target = new LinkedList<>();
        DataByEvents data = getStatsAndConfirmRequestsByEvents(events);

        events.forEach(u -> {
            if (!isNeedModeration || eventIdsNeedModeration.contains(u.getId())) {
                EventShortDto eventShortDto = toShortDto(u, data.getViews().getOrDefault(u.getId(), 0),
                        data.getConfirmRequests().getOrDefault(u.getId(), 0));
                if (!isOnlyAvailable || u.getParticipantLimit() == 0 || !u.getRequestModeration() ||
                        eventShortDto.getConfirmedRequests() < u.getParticipantLimit()) {
                    target.add(eventShortDto);
                }
            }
        });

        return target;
    }

    public List<EventFullDto> mapToFullEventDto(Iterable<Event> events) {
        List<EventFullDto> target = new LinkedList<>();
        DataByEvents data = getStatsAndConfirmRequestsByEvents(events);

        events.forEach(u -> target.add(toFullDto(u, data.getViews().getOrDefault(u.getId(), 0),
                                                    data.getConfirmRequests().getOrDefault(u.getId(), 0))));
        return target;
    }

    private DataByEvents getStatsAndConfirmRequestsByEvents(Iterable<Event> events) {
        DataByEvents data = new DataByEvents();

        final LocalDateTime[] start = {LocalDateTime.now()};

        List<Long> eventIds = new ArrayList<>();
        events.forEach(u -> {
            eventIds.add(u.getId());
            if (u.getCreatedOn().isBefore(start[0])) {
                start[0] = u.getCreatedOn();
            }
        });
        data.setViews(statsAgent.getStatsByEventIds(eventIds, start[0], LocalDateTime.now()));

        Map<Long, Integer> confirmRequests = new HashMap<>();
        requestRepository.findAll(QRequest.request.event.id.in(eventIds)
                         .and(QRequest.request.status.eq(RequestStatus.CONFIRMED)))
                         .forEach(request -> confirmRequests.put(request.getEvent().getId(),
                                  confirmRequests.getOrDefault(request.getEvent().getId(), 0) + 1));
        data.setConfirmRequests(confirmRequests);

        return data;
    }

    @Getter
    @Setter
    private static class DataByEvents {
        private Map<Long, Integer> views;
        private Map<Long, Integer> confirmRequests;
    }
}
