package com.snowball.board.domain.user.model;

import com.snowball.board.common.util.UserRole;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@DynamicInsert
@Table(name = "user_tb")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_account", length = 15, nullable = false, unique = true)
    private String userAccount;

    @Column(name = "user_name", length = 10, nullable = false)
    private String userName;

    @Column(length = 72, nullable = false)
    private String password;

    @Column(length = 30, nullable = false)
    private String email;

    @Column(name = "nick_name", length = 10, nullable = false, unique = true)
    private String nickName;

    @Column(name = "user_status", nullable = false, columnDefinition = "boolean default true")
    private boolean userStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", length = 20, nullable = false, columnDefinition = "varchar(20) default 'BEGINNER'")
    private UserRole userRole = UserRole.BEGINNER;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "timestamp default current_timestamp")
    private Timestamp updatedAt;
}
