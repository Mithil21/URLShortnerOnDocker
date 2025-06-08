package com.urlShortner.demo.user.repo;

import com.urlShortner.demo.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {

    Users findByUsername(String username);

    boolean existsByUsername(String username);

}
