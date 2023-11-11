package ru.practicum.explorewithme.service.request;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.out.ParticipationRequestDto;
import ru.practicum.explorewithme.model.Request;

import static ru.practicum.dto.ContextStats.formatter;

@Component
public class RequestMapper {
    public ParticipationRequestDto toDto(Request request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .created(formatter.format(request.getCreated()))
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus().toString())
                .build();
    }
}
