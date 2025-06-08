package com.urlShortner.demo.urlShortner.service;

import com.urlShortner.demo.urlShortner.model.URLShortner;
import com.urlShortner.demo.urlShortner.repo.URLShortnerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
public class URLShortnerService {
    @Autowired
    private URLShortnerRepo repository;

    //    Caching
    @Cacheable(value = "shortCodeCache", key = "#shortCode")
    public Optional<String> getOriginalUrl(String shortCode) {
        System.out.println("Fetching original URL from DB for: " + shortCode); // For demonstration
        // In a real app, this would query your PostgreSQL DB
        return repository.findByShortCode(shortCode).getOriginalUrl().describeConstable();
    }

    // Cache the mapping from original URL to short URL
    // This is useful to prevent generating new short URLs for existing original URLs
    @Cacheable(value = "originalUrlCache", key = "#originalUrl")
    public String getshortCode(String originalUrl) {
        System.out.println("Fetching short URL from DB for: " + originalUrl); // For demonstration
        // Check if original URL already exists in DB
        Optional<URLShortner> existingMapping = repository.findByOriginalUrl(originalUrl);
        if (existingMapping.isPresent()) {
            return existingMapping.get().getShortCode();
        }

        // If not found, generate new and put in cache

        String shortCode = Base64.getEncoder().encodeToString(originalUrl.getBytes()).substring(0, 8); // Simple encoding for demo; // Your logic for generating unique short codes
        URLShortner newMapping = new URLShortner(shortCode, originalUrl);
        repository.save(newMapping); // Save to DB

        // Put the newly created mapping into both caches
        // @CachePut always executes the method and caches the result
        // No explicit @CachePut needed here as @Cacheable will cache if not found,
        // and we are ensuring consistency by saving to DB.
        // If we needed to update an existing entry or ensure it's in cache after creation:
        // updateshortCodeCache(shortCode, originalUrl); // A helper method
        // updateOriginalUrlCache(originalUrl, shortCode); // A helper method
        return shortCode;
    }

    // Example of a helper method to manually update cache (optional, Spring often handles)
    @CachePut(value = "shortCodeCache", key = "#shortCode")
    public String updateshortCodeCache(String shortCode, String originalUrl) {
        return originalUrl;
    }

    @CachePut(value = "originalUrlCache", key = "#originalUrl")
    public String updateOriginalUrlCache(String originalUrl, String shortCode) {
        return shortCode;
    }

    // If you had a delete operation, you'd use @CacheEvict
    @CacheEvict(value = {"shortCodeCache", "originalUrlCache"}, allEntries = true)
    // Evicts all entries from both caches
    public void clearAllCaches() {
        System.out.println("Clearing all URL caches!");
    }
}
