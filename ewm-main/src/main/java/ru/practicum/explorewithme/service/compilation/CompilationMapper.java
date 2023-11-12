package ru.practicum.explorewithme.service.compilation;

import lombok.*;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.input.create.NewCompilationDto;
import ru.practicum.explorewithme.dto.output.CompilationDto;
import ru.practicum.explorewithme.dto.output.outshort.EventShortDto;
import ru.practicum.explorewithme.model.Compilation;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.QRequest;
import ru.practicum.explorewithme.model.enummodel.RequestStatus;
import ru.practicum.explorewithme.repository.RequestRepository;
import ru.practicum.explorewithme.service.client.StatsAgent;
import ru.practicum.explorewithme.service.event.EventMapper;

import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CompilationMapper {
    private final EventMapper eventMapper;

    private final RequestRepository requestRepository;

    private final StatsAgent statsAgent;

    public Compilation fromDto(NewCompilationDto newCompilationDto, Set<Event> events) {
        return Compilation.builder()
                .events(events)
                .pinned(newCompilationDto.getPinned())
                .title(newCompilationDto.getTitle())
                .build();
    }

    public CompilationDto toDto(Compilation compilation) {
        DataByEvents data = getStatsAndConfirmRequestsByEventsIds(compilation.getEvents());
        List<EventShortDto> events = eventMapper.mapToEventDto(compilation.getEvents(),
                data.getViews(), data.getConfirmRequests(), false);

        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .events(new HashSet<>(events))
                .build();
    }

    private DataByEvents getStatsAndConfirmRequestsByEventsIds(Set<Event> events) {
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
