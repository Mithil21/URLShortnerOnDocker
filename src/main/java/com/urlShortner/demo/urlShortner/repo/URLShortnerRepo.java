package com.urlShortner.demo.urlShortner.repo;

import com.urlShortner.demo.urlShortner.model.URLShortner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface URLShortnerRepo extends JpaRepository<URLShortner,Long> {
    URLShortner findByShortCode(String shortCode);

    Optional<URLShortner> findByOriginalUrl(String originalUrl);
}
