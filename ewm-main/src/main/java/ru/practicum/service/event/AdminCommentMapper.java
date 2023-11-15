package ru.practicum.service.event;

import org.springframework.stereotype.Component;
import ru.practicum.dto.output.AdminCommentDto;
import ru.practicum.model.AdminComment;

import java.util.LinkedList;
import java.util.List;

import static ru.practicum.dto.ContextStats.formatter;

@Component
public class AdminCommentMapper {
    public AdminCommentDto toDto(AdminComment comment) {
        return AdminCommentDto.builder()
                .id(comment.getId())
                .created(formatter.format(comment.getCreated()))
                .eventId(comment.getEvent().getId())
                .text(comment.getText())
                .state(comment.getState().toString())
                .build();
    }

    public List<AdminCommentDto> mapToDto(Iterable<AdminComment> comments) {
        List<AdminCommentDto> target = new LinkedList<>();
        comments.forEach(u -> target.add(toDto(u)));
        return target;
    }
}
