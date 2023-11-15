package ru.practicum.model;

import lombok.*;
import ru.practicum.model.enummodel.CommentState;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin_comments")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Getter
@Setter
public class AdminComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id", nullable = false)
    private final Event event;
    private String text;
    private LocalDateTime created;
    @Column(name = "state_comment", nullable = false)
    @Enumerated(EnumType.STRING)
    private CommentState state;
}
