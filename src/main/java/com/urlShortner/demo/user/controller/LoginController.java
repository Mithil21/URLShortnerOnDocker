package com.urlShortner.demo.user.controller;

import com.urlShortner.demo.user.dto.LoginDto;
import com.urlShortner.demo.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/register")
    public String login(@RequestBody LoginDto input) {
        loginService.Register(input.getUsername(), input.getPassword());
        return "Succcessfully Logged In";
    }
}
