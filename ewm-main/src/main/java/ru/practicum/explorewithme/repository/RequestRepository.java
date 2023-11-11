package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.explorewithme.model.Request;
import ru.practicum.explorewithme.model.enummodel.RequestStatus;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long>, QuerydslPredicateExecutor<Request> {
    boolean existsByEventIdAndRequesterId(Long eventId, Long userId);

    Integer countByEvent_IdAndStatus(Long eventId, RequestStatus status);

    List<Request> findByRequester_Id(Long requesterId);

    List<Request> findByEvent_Id(Long eventId);
}
