package ru.practicum.explorewithme.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class EventRequestStatusUpdateResult {
    private Set<ParticipationRequestDto> confirmedRequests;
    private Set<ParticipationRequestDto> rejectedRequests;
}
