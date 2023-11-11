package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.enummodel.EventState;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {
    Optional<Event> findByIdAndInitiatorId(Long id, Long initiatorId);

    Optional<Event> findByIdAndState(Long id, EventState state);

    Boolean existsByCategoryId(Long catId);
}
