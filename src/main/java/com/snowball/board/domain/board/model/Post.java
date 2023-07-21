package com.snowball.board.domain.board.model;
import com.snowball.board.domain.user.model.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, length = 1000)
    private String content;
    @Column(nullable = false)
    private boolean blindState;
    @CreatedDate
    @Column (updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column (updatable = true)
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;
}