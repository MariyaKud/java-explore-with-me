package ru.practicum.explorewithme.exeption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError {
    String message;
    String reason;
    String status;
    String timestamp;
    String errors;
}
