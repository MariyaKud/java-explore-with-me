package ru.practicum.explorewithme.dto.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.dto.roster.StatusEventRequest;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ParticipationRequestDto {
    private Long id;
    private LocalDateTime created;
    private StatusEventRequest status;
    private Long event;
    private Long requester;
}
