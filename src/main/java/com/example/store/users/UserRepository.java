package com.example.store.users;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "profile")
    @Query("select u from User u")
    List<User> getAllWithProfile(Sort sort);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
