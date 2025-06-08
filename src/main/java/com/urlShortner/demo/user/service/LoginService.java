package com.urlShortner.demo.user.service;

import com.urlShortner.demo.user.Authorities;
import com.urlShortner.demo.user.Users;
import com.urlShortner.demo.user.repo.AuthoritiesRepo;
import com.urlShortner.demo.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthoritiesRepo authoritiesRepo;

    public boolean Register(String username, String password) {
        // Logic to register a user
        // This is a placeholder implementation
        Users users = new Users();
        users.setEnabled(Boolean.TRUE);
        users.setUsername(username);
        users.setPassword(password);
        userRepository.save(users);

        Authorities authorities = new Authorities();
        authorities.setUsername(username);
        authorities.setAuthority("ROLE_USER");
        authoritiesRepo.save(authorities);
        return true;
    }
}
