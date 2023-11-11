package ru.practicum.explorewithme.service.event;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ru.practicum.explorewithme.dto.in.create.NewEventDto;
import ru.practicum.explorewithme.dto.in.update.UpdateEventAdminRequest;
import ru.practicum.explorewithme.dto.in.update.UpdateEventDto;
import ru.practicum.explorewithme.dto.in.update.UpdateEventUserRequest;
import ru.practicum.explorewithme.dto.out.EventFullDto;
import ru.practicum.explorewithme.dto.out.outshort.EventShortDto;
import ru.practicum.explorewithme.dto.param.AdminEventParam;
import ru.practicum.explorewithme.dto.param.EventParam;
import ru.practicum.explorewithme.exeption.NotFoundException;
import ru.practicum.explorewithme.exeption.NotMeetRulesException;
import ru.practicum.explorewithme.model.*;
import ru.practicum.explorewithme.model.enummodel.*;
import ru.practicum.explorewithme.repository.*;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.service.client.StatsAgent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements PrivateEventService, AdminEventService, PublicEventService {
    private final EventRepository eventRepository;

    private final CategoryRepository categoryRepository;

    private final LocationRepository locationRepository;

    private final UserRepository userRepository;

    private final RequestRepository requestRepository;

    private final StatsAgent statsAgent;

    private final EventMapper eventMapper;

    @Override
    @Transactional
    public EventFullDto createUserEvent(Long userId, NewEventDto newEventDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(userId, User.class));

        final Event newEvent = eventMapper.fromDto(newEventDto);
        enrichCategoryInEvent(newEvent, newEventDto.getCategory());
        enrichLocationInEvent(newEvent, newEventDto.getLocation());
        newEvent.setCreatedOn(LocalDateTime.now());
        newEvent.setState(EventState.PENDING);
        newEvent.setInitiator(user);

        eventRepository.save(newEvent);

        return eventMapper.toFullDto(newEvent, 0, 0);
    }

    @Override
    @Transactional
    public EventFullDto updateUserEvent(Long userId, Long eventId, UpdateEventUserRequest eventDto) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(userId, User.class);
        }

        if (eventDto.getEventDate() != null && eventDto.getEventDate().plusHours(2).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(String.format("You are late with the publication of the event (id = %s)" +
                    " there is less than an 2 hour left before the start of the event", eventId));
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(eventId, Event.class));

        if (event.getState() == EventState.PUBLISHED) {
            throw new NotMeetRulesException(String.format("You cannot update an event (id = %s) with the status %s",
                    eventId, event.getState()));
        }

        if (eventDto.getStateAction() == EventStateActionUser.SEND_TO_REVIEW &&
                event.getEventDate().plusHours(2).isBefore(LocalDateTime.now())) {
            throw new NotMeetRulesException(String.format("You are late with the publication of the event (id = %s)" +
                    " there is less than an two hour left before the start of the event", eventId));
        }

        if (eventDto.isStateAction()) {
            switch (eventDto.getStateAction()) {
                case CANCEL_REVIEW: {
                    event.setState(EventState.CANCELED);
                    break;
                }
                case SEND_TO_REVIEW: {
                    event.setState(EventState.PENDING);
                    break;
                }
            }
        }

        updatePropertyEvent(event, eventDto);

        return eventMapper.toFullDto(event, statsAgent.getStatsByEventId(eventId, event.getCreatedOn(),
                LocalDateTime.now()), requestRepository.countByEvent_IdAndStatus(eventId, RequestStatus.CONFIRMED));
    }

    @Override
    @Transactional
    public EventFullDto updateAdminEvent(Long eventId, UpdateEventAdminRequest eventDto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(eventId, Event.class));

        LocalDateTime publishDate = LocalDateTime.now();

        if (eventDto.getEventDate() != null && eventDto.getEventDate().plusHours(1).isBefore(publishDate)) {
            throw new IllegalArgumentException(String.format("You are late with the publication of the event (id = %s)" +
                    " there is less than an hour left before the start of the event", eventId));
        }

        if (eventDto.getStateAction() == EventStateActionAdmin.PUBLISH_EVENT) {
            if (event.getState() != EventState.PENDING) {
                throw new NotMeetRulesException(String.format("You cannot publish an event (id = %s) with the status %s",
                        eventId, event.getState()));
            } else {
                event.setPublishedOn(publishDate);
                event.setState(EventState.PUBLISHED);
            }
        }

        if (eventDto.getStateAction() == EventStateActionAdmin.REJECT_EVENT) {
            if (event.getState() == EventState.PUBLISHED) {
                throw new NotMeetRulesException(String.format("You cannot reject published an event (id = %s)", eventId));
            } else {
                event.setState(EventState.CANCELED);
            }
        }

        updatePropertyEvent(event, eventDto);

        return eventMapper.toFullDto(event, statsAgent.getStatsByEventId(eventId, event.getCreatedOn(),
                LocalDateTime.now()), requestRepository.countByEvent_IdAndStatus(eventId, RequestStatus.CONFIRMED));
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto findUserEventById(Long userId, Long eventId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(userId, User.class);
        }

        return eventRepository.findByIdAndInitiatorId(eventId, userId)
                .map(e -> eventMapper.toFullDto(e, statsAgent.getStatsByEventId(
                                e.getId(), e.getCreatedOn(), LocalDateTime.now()),
                        requestRepository.countByEvent_IdAndStatus(e.getId(), RequestStatus.CONFIRMED)))
                .orElseThrow(() -> new NotFoundException(eventId, Event.class));
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto findEventById(Long eventId) {
        Event event = eventRepository.findByIdAndState(eventId, EventState.PUBLISHED)
                .orElseThrow(() -> new NotFoundException(eventId, Event.class));

        return eventMapper.toFullDto(event, statsAgent.getStatsByEventId(eventId, event.getCreatedOn(),
                LocalDateTime.now()), requestRepository.countByEvent_IdAndStatus(eventId, RequestStatus.CONFIRMED));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getUserEvents(Long userId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size, Sort.by("id"));

        BooleanExpression byUserIds = QEvent.event.initiator.id.eq(userId);
        Iterable<Event> foundEvents = eventRepository.findAll(byUserIds, pageable);
        DataByEvents data = getStatsAndConfirmRequestsByEvents(foundEvents);

        return eventMapper.mapToEventDto(foundEvents, data.getViews(), data.getConfirmRequests(), false);
    }

    @Override
    public List<EventFullDto> getAdminEvents(AdminEventParam eventParam, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size, Sort.by("id"));

        BooleanBuilder builder = new BooleanBuilder();
        if (eventParam.isUsers()) {
            builder.and(QEvent.event.initiator.id.in(eventParam.getUsers()));
        }
        if (eventParam.isCategories()) {
            builder.and(QEvent.event.category.id.in(eventParam.getCategories()));
        }
        if (eventParam.isStates()) {
            builder.and(QEvent.event.state.in(eventParam.getStates()));
        }
        if (eventParam.isRangeStart()) {
            builder.and(QEvent.event.eventDate.goe(eventParam.getRangeStart()));
        }
        if (eventParam.isRangeEnd()) {
            builder.and(QEvent.event.eventDate.loe(eventParam.getRangeEnd()));
        }

        Iterable<Event> foundEvents = eventRepository.findAll(builder, pageable);
        DataByEvents data = getStatsAndConfirmRequestsByEvents(foundEvents);

        return eventMapper.mapToFullEventDto(foundEvents, data.getViews(), data.getConfirmRequests());
    }

    @Override
    public List<EventShortDto> getEvents(EventParam eventParam, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size);
        if (eventParam.isSort() && eventParam.getSort() == EventSort.EVENT_DATE) {
            pageable = PageRequest.of(from > 0 ? from / size : 0, size, Sort.by("eventDate"));
        }

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QEvent.event.state.eq(EventState.PUBLISHED));
        if (eventParam.isText()) {
            BooleanExpression byAnnotation = QEvent.event.annotation.containsIgnoreCase(eventParam.getText());
            BooleanExpression byDescription = QEvent.event.description.containsIgnoreCase(eventParam.getText());

            builder.andAnyOf(byAnnotation, byDescription);
        }
        if (eventParam.isPaid()) {
            builder.and(QEvent.event.paid.eq(eventParam.getPaid()));
        }
        if (eventParam.isCategories()) {
            builder.and(QEvent.event.category.id.in(eventParam.getCategories()));
        }
        if (eventParam.isRangeStart()) {
            builder.and(QEvent.event.eventDate.goe(eventParam.getRangeStart()));
        } else {
            builder.and(QEvent.event.eventDate.goe(LocalDateTime.now()));
        }
        if (eventParam.isRangeEnd()) {
            builder.and(QEvent.event.eventDate.loe(eventParam.getRangeEnd()));
        }
        boolean isOnlyAvailable = false;
        if (eventParam.isOnlyAvailable()) {
            isOnlyAvailable = eventParam.getOnlyAvailable();
        }

        Iterable<Event> foundEvents = eventRepository.findAll(builder, pageable);
        DataByEvents data = getStatsAndConfirmRequestsByEvents(foundEvents);

        return eventMapper.mapToEventDto(foundEvents, data.getViews(), data.getConfirmRequests(), isOnlyAvailable);
    }

    private void enrichCategoryInEvent(Event event, Long catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(() ->
                new NotFoundException(catId, Category.class));

        event.setCategory(category);
    }

    private void enrichLocationInEvent(Event event, Location location) {
        List<Location> locations = locationRepository.findByLatAndLon(location.getLat(), location.getLon());

        if (!locations.isEmpty()) {
            event.setLocation(locations.get(0));
        } else {
            event.setLocation(locationRepository.save(location));
        }
    }

    private <T extends UpdateEventDto> void updatePropertyEvent(Event event, T eventDto) {
        if (eventDto.isAnnotation()) {
            event.setAnnotation(eventDto.getAnnotation());
        }
        if (eventDto.isEventDate()) {
            event.setEventDate(eventDto.getEventDate());
        }
        if (eventDto.isDescription()) {
            event.setDescription(eventDto.getDescription());
        }
        if (eventDto.isPaid()) {
            event.setPaid(eventDto.getPaid());
        }
        if (eventDto.isParticipantLimit()) {
            event.setParticipantLimit(eventDto.getParticipantLimit());
        }
        if (eventDto.isRequestModeration()) {
            event.setRequestModeration(eventDto.getRequestModeration());
        }
        if (eventDto.isTitle()) {
            event.setTitle(eventDto.getTitle());
        }
        if (eventDto.isCategory()) {
            enrichCategoryInEvent(event, eventDto.getCategory());
        }
        if (eventDto.isLocation()) {
            enrichLocationInEvent(event, eventDto.getLocation());
        }
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
