package ru.practicum.explorewithme.service.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.in.update.EventRequestStatusUpdateRequest;
import ru.practicum.explorewithme.dto.out.EventRequestStatusUpdateResult;
import ru.practicum.explorewithme.dto.out.ParticipationRequestDto;
import ru.practicum.explorewithme.exeption.NotFoundException;
import ru.practicum.explorewithme.exeption.NotMeetRulesException;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.Request;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.model.enummodel.EventState;
import ru.practicum.explorewithme.model.enummodel.RequestStatus;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.repository.RequestRepository;
import ru.practicum.explorewithme.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    private final RequestMapper requestMapper;

    @Override
    public ParticipationRequestDto createUserRequest(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new NotFoundException(userId, User.class));

        Event event = eventRepository.findById(eventId)
                                     .orElseThrow(() -> new NotFoundException(eventId, Event.class));

        if (requestRepository.existsByEvent_Id(eventId)) {
            throw new NotMeetRulesException(String.format("Participation request to event id = %s also exist", eventId));
        }

        if (Objects.equals(user.getId(), event.getInitiator().getId())) {
            throw new NotMeetRulesException(String.format("Participation request to own event id = %s", eventId));
        }

        if (event.getState() != EventState.PUBLISHED) {
            throw new NotMeetRulesException(String.format("Participation request to not publish event id = %s", eventId));
        }

        if (Objects.equals(event.getParticipantLimit(), requestRepository.countByEvent_Id(eventId))) {
            throw new NotMeetRulesException(String.format("Participation request to event id = %s but limit overflow", eventId));
        }

        Request request = Request.builder()
                .created(LocalDateTime.now())
                .requester(user)
                .event(event)
                .status(RequestStatus.PENDING)
                .build();

        if (!event.getRequestModeration()) {
            request.setStatus(RequestStatus.CONFIRMED);
        }

        return requestMapper.toDto(requestRepository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> getUserRequests(Long userId) {
        return null;
    }

    @Override
    public Boolean cancelUserRequest(Long userId, Long requestId) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> findRequestsForUserEventById(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventRequestStatusUpdateResult updateStatusRequestsForUserEvent(Long userId, Long eventId,
                                           EventRequestStatusUpdateRequest statusRequests) {
        return null;
    }
}
