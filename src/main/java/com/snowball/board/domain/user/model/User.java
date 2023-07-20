package com.snowball.board.domain.user.model;

import com.snowball.board.common.util.UserRole;
import com.snowball.board.domain.token.model.Token;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user_tb")
@DynamicInsert
@DynamicUpdate
public class User implements UserDetails {

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
    @ColumnDefault("'BEGINNER'")
    private UserRole userRole;

    @Column(name = "created_at")
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateNickName(String nickName) {
        this.nickName = nickName;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @OneToMany(mappedBy = "user")
    public List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.getUserRole().name()));
        return authorities;
    }

    //Username -> userAccount
    //UserName -> userName
    @Override
    public String getUsername() {
        return userAccount;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
