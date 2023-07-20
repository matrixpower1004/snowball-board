package com.snowball.board.domain.user.repository;

import com.snowball.board.domain.user.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserAccount(String userAccount);

    Optional<User> findByNickName(String nickName);

    @Query("SELECT u, COUNT(p.id) FROM User u LEFT JOIN Post p ON u.id = p.user.id GROUP BY u.id HAVING COUNT(p.id) > :postCountThreshold")
    List<User> getUsersWithPostCountGreaterThan(@Param("postCountThreshold") Long postCountThreshold);

}
