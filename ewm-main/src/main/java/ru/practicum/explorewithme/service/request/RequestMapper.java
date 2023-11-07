package ru.practicum.explorewithme.service.request;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.out.ParticipationRequestDto;
import ru.practicum.explorewithme.model.Request;

@Component
public class RequestMapper {
    public ParticipationRequestDto toDto(Request request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .created(request.getCreated().toString())
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus().toString())
                .build();
    }
}
