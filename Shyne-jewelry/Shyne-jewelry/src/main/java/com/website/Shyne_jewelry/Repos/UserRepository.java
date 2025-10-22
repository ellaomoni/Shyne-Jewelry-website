package com.website.Shyne_jewelry.Repos;

import com.website.Shyne_jewelry.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long>{
    Optional<User> findByEmail(String name);
    Boolean existsByEmail(String email);
    Optional<User> findByVerificationToken(String token);


}
