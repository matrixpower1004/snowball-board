package com.snowball.board.domain.user.repository;

import com.snowball.board.domain.user.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserAccount(String userAccount);

    Optional<User> findByNickName(String nickName);

    Optional<User> findByEmail(String email);


    @Query("SELECT u, COUNT(p.id) FROM User u LEFT JOIN Post p ON u.id = p.user.id GROUP BY u.id HAVING COUNT(p.id) > :postCountThreshold")
    List<User> getUsersWithPostCountGreaterThan(@Param("postCountThreshold") Long postCountThreshold);

}
