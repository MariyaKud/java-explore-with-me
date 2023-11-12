package ru.practicum.explorewithme.dto.input.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explorewithme.model.enummodel.RequestStatusUpdate;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class EventRequestStatusUpdateRequest {
    private Set<Long> requestIds;
    private RequestStatusUpdate status;
}
