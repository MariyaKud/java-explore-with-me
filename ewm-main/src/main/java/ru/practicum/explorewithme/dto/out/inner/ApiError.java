package ru.practicum.explorewithme.dto.out.inner;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ApiError {
    List<String> errors;
    String message;
    String reason;
    String status;
    LocalDateTime timestamp;
}
