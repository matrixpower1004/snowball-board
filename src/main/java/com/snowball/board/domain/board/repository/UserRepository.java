package com.snowball.board.domain.board.repository;

import com.snowball.board.domain.board.emtity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserAccount(String userAccount);
}
