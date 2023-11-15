package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.model.AdminComment;

import java.util.List;

public interface AdminCommentRepository extends JpaRepository<AdminComment, Long>, QuerydslPredicateExecutor<AdminComment> {
    List<AdminComment> findByEvent_Id(Long eventId);
}
