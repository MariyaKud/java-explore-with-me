package ru.practicum.explorewithme.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.in.create.NewEventDto;
import ru.practicum.explorewithme.dto.out.EventFullDto;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.repository.EventRepository;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{
    private final EventRepository eventRepository;

    private final EventMapper eventMapper;

    @Override
    public EventFullDto createEvent(Long userId, NewEventDto newEventDto) {
        final Event newEvent = eventMapper.fromDto(newEventDto);
        eventRepository.save(newEvent);
        return eventMapper.toFullDto(newEvent);
    }
}
