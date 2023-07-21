package com.snowball.board.domain.board.model;

import com.snowball.board.domain.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "comment")
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    private Post post;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());

    @Column(nullable = false)
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<Reply> replies;
    public Optional<Long> getUserId() {
        return Optional.ofNullable(user).map(User::getId);
    }
}