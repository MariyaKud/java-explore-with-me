package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.model.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {
    boolean existsByEvent_Id(Long eventId);

    Integer countByEvent_Id(Long event_id);
}
