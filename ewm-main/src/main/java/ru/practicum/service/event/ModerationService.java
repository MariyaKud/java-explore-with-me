package ru.practicum.service.event;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ru.practicum.dto.output.AdminCommentDto;
import ru.practicum.exeption.NotFoundException;
import ru.practicum.exeption.NotMeetRulesException;
import ru.practicum.model.AdminComment;
import ru.practicum.model.Event;
import ru.practicum.model.QAdminComment;
import ru.practicum.model.enummodel.CommentState;
import ru.practicum.model.enummodel.EventState;
import ru.practicum.repository.AdminCommentRepository;
import ru.practicum.repository.EventRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ModerationService implements AdminModerationService, PrivateModerationService {
    private final EventRepository eventRepository;

    private final AdminCommentRepository commentRepository;

    private final AdminCommentMapper commentMapper;

    @Override
    public AdminCommentDto createComment(Long eventId, String text) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(eventId, Event.class));

        if (event.getState() != EventState.CANCELED) {
            throw new NotMeetRulesException(String.format("You cannot comment an event (id = %s) with the status %s",
                    eventId, event.getState()));
        }

        AdminComment comment = commentRepository.save(AdminComment.builder()
                .state(CommentState.PENDING)
                .text(text)
                .created(LocalDateTime.now())
                .event(event)
                .build());

        event.getAdminComments().add(comment);
        eventRepository.save(event);

        return commentMapper.toDto(comment);
    }

    @Override
    public List<AdminCommentDto> getComments(List<Long> eventIds, boolean isFixed, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size);

        BooleanBuilder builder = new BooleanBuilder();
        if (!eventIds.isEmpty()) {
            builder.and(QAdminComment.adminComment.event.id.in(eventIds));
        }
        if (isFixed) {
            builder.and(QAdminComment.adminComment.state.eq(CommentState.FIXED));
        }
        Iterable<AdminComment> foundComments = commentRepository.findAll(builder, pageable);

        return commentMapper.mapToDto(foundComments);
    }

    @Override
    public List<AdminCommentDto> getUserComments(Long userId, boolean isPending, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size);

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QAdminComment.adminComment.event.initiator.id.eq(userId));
        if (isPending) {
            builder.and(QAdminComment.adminComment.state.eq(CommentState.PENDING));
        }
        Iterable<AdminComment> foundComments = commentRepository.findAll(builder, pageable);

        return commentMapper.mapToDto(foundComments);
    }
}
