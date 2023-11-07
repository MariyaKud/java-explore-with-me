package ru.practicum.explorewithme.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.practicum.explorewithme.dto.in.create.NewEventDto;
import ru.practicum.explorewithme.dto.in.update.UpdateEventAdminRequest;
import ru.practicum.explorewithme.dto.in.update.UpdateEventUserRequest;
import ru.practicum.explorewithme.dto.out.EventFullDto;
import ru.practicum.explorewithme.dto.out.outshort.EventShortDto;
import ru.practicum.explorewithme.dto.param.AdminEventParam;
import ru.practicum.explorewithme.exeption.NotFoundException;
import ru.practicum.explorewithme.model.*;
import ru.practicum.explorewithme.model.enummodel.EventState;
import ru.practicum.explorewithme.repository.CategoryRepository;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.repository.LocationRepository;
import ru.practicum.explorewithme.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    private final CategoryRepository categoryRepository;

    private final LocationRepository locationRepository;

    private final UserRepository userRepository;

    private final EventMapper eventMapper;

    @Override
    @Transactional
    public EventFullDto createEvent(Long userId, NewEventDto newEventDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId, User.class));

        final Event newEvent = eventMapper.fromDto(newEventDto);
        enrichCategoryInEvent(newEvent, newEventDto.getCategory());
        enrichLocationInEvent(newEvent, newEventDto.getLocation());
        newEvent.setCreatedOn(LocalDateTime.now());
        newEvent.setState(EventState.PENDING);
        newEvent.setInitiator(user);

        eventRepository.save(newEvent);
        return eventMapper.toFullDto(newEvent);
    }

    @Override
    public List<EventShortDto> getUserEvents(Long userId, Integer from, Integer size) {
        return null;
    }

    @Override
    public EventFullDto findUserEventById(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new NotFoundException(userId, User.class));

        return eventRepository.findById(eventId)
                .map(eventMapper::toFullDto)
                .orElseThrow(() -> new NotFoundException(eventId, Event.class));

    }

    @Override
    public EventFullDto updateUserEvent(Long userId, Long eventId, UpdateEventUserRequest eventDto) {
        return null;
    }

    @Override
    public List<EventShortDto> getEvents(AdminEventParam eventParam, Integer from, Integer size) {
        return null;
    }

    @Override
    public EventFullDto updateAdminEvent(Long eventId, UpdateEventAdminRequest eventDto) {
        return null;
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
}
