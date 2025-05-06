package com.turaninarcis.group_activity_planner.Users;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turaninarcis.group_activity_planner.Users.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);
    Optional<User> findById(UUID id);
    User findByEmail(String email);
    User findByEmailOrUsername(String email, String username);
}
