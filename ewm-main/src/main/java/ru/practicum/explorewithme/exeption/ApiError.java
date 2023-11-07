package ru.practicum.explorewithme.exeption;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiError {
    String errors;
    String message;
    String reason;
    String status;
    String timestamp;
}
