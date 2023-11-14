package ru.practicum.dto.input.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import ru.practicum.model.enummodel.EventStateActionUser;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventUserRequest extends UpdateEventRequestBase {
    private EventStateActionUser stateAction;

    public boolean isStateAction() {
        return stateAction != null;
    }
}
