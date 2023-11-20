package ru.practicum.dto.output;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class AdminCommentDto {
    private Long id;
    private Long eventId;
    private String text;
    private String created;
    private String state;
}
