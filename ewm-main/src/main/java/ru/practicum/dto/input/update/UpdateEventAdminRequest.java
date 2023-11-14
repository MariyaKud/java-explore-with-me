package ru.practicum.dto.input.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import ru.practicum.model.enummodel.EventStateActionAdmin;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventAdminRequest extends UpdateEventRequestBase {
    private EventStateActionAdmin stateAction;

    public boolean isStateAction() {
        return stateAction != null;
    }
}
