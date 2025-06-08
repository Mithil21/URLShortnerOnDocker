package com.urlShortner.demo.urlShortner.controller;

import com.urlShortner.demo.urlShortner.dto.URLRequestDto;
import com.urlShortner.demo.urlShortner.service.URLShortnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/url")
public class URLShortnerController {

    @Autowired
    private URLShortnerService service;

    @PostMapping("/shorten")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> shortenUrl(@RequestBody URLRequestDto originalUrl) {
        String shortCode = service.getshortCode(originalUrl.getOriginalUrl());
        return ResponseEntity.ok("To get the short url call: - http://localhost:8080/web/url/shortCode" + "\nPass the payload as {'shortUrl':"+shortCode+"}");
    }

    @PostMapping("/shortCode")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> resolveUrl(@RequestBody URLRequestDto shortCode) {
        String originalUrl = service.getOriginalUrl(shortCode.getShortCode()).orElse("URL not found");
        return ResponseEntity.ok(originalUrl);
    }
}
