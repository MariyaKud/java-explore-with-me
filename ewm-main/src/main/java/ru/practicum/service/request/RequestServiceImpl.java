package ru.practicum.service.request;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.input.update.EventRequestStatusUpdateRequest;
import ru.practicum.dto.output.EventRequestStatusUpdateResult;
import ru.practicum.dto.output.ParticipationRequestDto;
import ru.practicum.exeption.NotFoundException;
import ru.practicum.exeption.NotFoundListException;
import ru.practicum.exeption.NotMeetRulesException;
import ru.practicum.model.*;
import ru.practicum.model.Event;
import ru.practicum.model.Request;
import ru.practicum.model.User;
import ru.practicum.model.enummodel.EventState;
import ru.practicum.model.enummodel.RequestStatus;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.RequestRepository;
import ru.practicum.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements PrivateRequestService {
    private final RequestRepository requestRepository;

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    private final RequestMapper requestMapper;

    @Override
    @Transactional
    public ParticipationRequestDto createUserRequest(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(userId, User.class));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(eventId, Event.class));

        if (requestRepository.existsByEventIdAndRequesterId(eventId, userId)) {
            throw new NotMeetRulesException(String.format("Participation request to event id=%s " +
                    "for user id=%s also exist", eventId, userId));
        }

        if (Objects.equals(user.getId(), event.getInitiator().getId())) {
            throw new NotMeetRulesException(String.format("Participation request to own event id = %s", eventId));
        }

        if (event.getState() != EventState.PUBLISHED) {
            throw new NotMeetRulesException(String.format("Participation request to not publish event id = %s", eventId));
        }

        if (event.getParticipantLimit() > 0 &&
                Objects.equals(event.getParticipantLimit(), requestRepository.countByEvent_IdAndStatus(eventId,
                        RequestStatus.CONFIRMED))) {
            throw new NotMeetRulesException(String.format("Participation request to event id = %s " +
                    "but limit overflow", eventId));
        }

        Request request = Request.builder()
                .created(LocalDateTime.now())
                .requester(user)
                .event(event)
                .status(RequestStatus.PENDING)
                .build();

        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            request.setStatus(RequestStatus.CONFIRMED);
        }

        return requestMapper.toDto(requestRepository.save(request));
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateStatusRequestsForUserEvent(Long userId, Long eventId,
                                                                           EventRequestStatusUpdateRequest statusRequests) {
        //Проверка требований
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(userId, User.class);
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(eventId, Event.class));

        if (!event.getRequestModeration()) {
            throw new NotMeetRulesException(String.format("Moderation for event id = %s " +
                    "is absent", eventId));
        }

        if (event.getParticipantLimit() == 0) {
            throw new NotMeetRulesException(String.format("The limit for participation in the event id = %s " +
                    "is equal 0.", eventId));
        }

        final BooleanExpression byIds = QRequest.request.id.in(statusRequests.getRequestIds());
        final BooleanExpression byState = QRequest.request.status.eq(RequestStatus.PENDING).not();
        Iterable<Request> findRequests = requestRepository.findAll(byIds.and(byState));
        List<Long> target = new ArrayList<>();
        findRequests.forEach(u -> target.add(u.getId()));
        if (findRequests.spliterator().getExactSizeIfKnown() > 0) {
            throw new NotMeetRulesException(String.format("In list %s you choose not pending participation", target));
        }

        final Integer quantityRequest = requestRepository.countByEvent_IdAndStatus(eventId, RequestStatus.CONFIRMED);

        if (event.getParticipantLimit() <= quantityRequest) {
            throw new NotMeetRulesException(String.format("The limit for participation in the event id = %s " +
                    "has been exhausted ", eventId));
        }

        //Основная часть
        findRequests = requestRepository.findAll(byIds);
        long quantityFindRequest = findRequests.spliterator().getExactSizeIfKnown();
        if (statusRequests.getRequestIds().size() != quantityFindRequest) {
            throw new NotFoundListException(statusRequests.getRequestIds(), Event.class);
        }

        long free = event.getParticipantLimit() - quantityRequest;

        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();
        Set<ParticipationRequestDto> confirmedRequests = new HashSet<>();
        Set<ParticipationRequestDto> rejectedRequests = new HashSet<>();

        switch (statusRequests.getStatus()) {
            case REJECTED: {
                findRequests.forEach(u -> {
                    u.setStatus(RequestStatus.REJECTED);
                    rejectedRequests.add(requestMapper.toDto(u));
                });
                break;
            }
            case CONFIRMED: {
                if (free >= quantityFindRequest) {
                    findRequests.forEach(u -> {
                        u.setStatus(RequestStatus.CONFIRMED);
                        confirmedRequests.add(requestMapper.toDto(u));
                    });
                } else {
                    Iterator<Request> itr = findRequests.iterator();
                    Request req;
                    while (itr.hasNext()) {
                        req = itr.next();
                        if (free > 0) {
                            req.setStatus(RequestStatus.CONFIRMED);
                            confirmedRequests.add(requestMapper.toDto(req));
                        } else {
                            req.setStatus(RequestStatus.REJECTED);
                            rejectedRequests.add(requestMapper.toDto(req));
                        }
                        free = free - 1;
                    }
                }
                break;
            }
        }

        result.setRejectedRequests(rejectedRequests);
        result.setConfirmedRequests(confirmedRequests);

        return result;
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelUserRequest(Long userId, Long requestId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(userId, User.class);
        }
        Request findRequest = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException(requestId, Request.class));

        findRequest.setStatus(RequestStatus.CANCELED);
        return requestMapper.toDto(findRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getUserRequests(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(userId, User.class);
        }

        return requestRepository.findByRequester_Id(userId)
                .stream()
                .map(requestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> findRequestsForUserEventById(Long userId, Long eventId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(userId, User.class);
        }

        if (!eventRepository.existsById(eventId)) {
            throw new NotFoundException(eventId, Event.class);
        }

        return requestRepository.findByEvent_Id(eventId)
                .stream()
                .map(requestMapper::toDto)
                .collect(Collectors.toList());
    }
}
