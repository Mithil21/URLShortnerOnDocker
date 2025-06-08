package com.urlShortner.demo.user.repo;

import com.urlShortner.demo.user.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritiesRepo extends JpaRepository<Authorities,String> {
}
