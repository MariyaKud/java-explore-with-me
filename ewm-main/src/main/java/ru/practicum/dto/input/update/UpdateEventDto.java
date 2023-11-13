package ru.practicum.dto.input.update;

import ru.practicum.model.Location;

import java.time.LocalDateTime;

public interface UpdateEventDto {

    boolean isAnnotation();

    String getAnnotation();

    boolean isDescription();

    String getDescription();

    boolean isTitle();

    String getTitle();

    boolean isCategory();

    Long getCategory();

    boolean isPaid();

    Boolean getPaid();

    boolean isParticipantLimit();

    Integer getParticipantLimit();

    boolean isRequestModeration();

    Boolean getRequestModeration();

    boolean isEventDate();

    LocalDateTime getEventDate();

    boolean isLocation();

    Location getLocation();
}
