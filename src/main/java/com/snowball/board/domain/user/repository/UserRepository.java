package com.snowball.board.domain.user.repository;

import com.snowball.board.domain.user.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserAccount(String userAccount);

    Optional<User> findByNickName(String nickName);

}
