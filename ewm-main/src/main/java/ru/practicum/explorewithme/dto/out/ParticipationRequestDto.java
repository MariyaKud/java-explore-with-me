package ru.practicum.explorewithme.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class ParticipationRequestDto {
    private Long id;
    private String created;
    private String status;
    private Long event;
    private Long requester;
}
