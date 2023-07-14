package com.snowball.board.domain.user.model;

import com.snowball.board.common.util.UserRole;
import lombok.Getter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Table(name = "user_tb")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_account")
    private String userAccount;

    @Column(name = "user_name")
    private String userName;

    private String password;

    private String email;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "user_status")
    private boolean userStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole = UserRole.BEGINNER;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
