package com.toy.springboard.springboard.user.repository;

import com.toy.springboard.springboard.user.entity.User;
import java.nio.channels.FileChannel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.posts WHERE u.email = :email")
    Optional<User> findUserWithPostsByEmail(String email);

    Optional<User> findByName(String username);
}
