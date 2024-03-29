package com.example.springsocial.controller;

import com.example.springsocial.exception.ResourceNotFoundException;
import com.example.springsocial.model.User;
import com.example.springsocial.model.dto.UserSsoDTO;
import com.example.springsocial.repository.UserRepository;
import com.example.springsocial.security.CurrentUser;
import com.example.springsocial.security.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSsoDTO getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        UserSsoDTO userRes = new UserSsoDTO();
        userRes.setId(userPrincipal.getId());
        userRes.setEmail(userPrincipal.getEmail());
        userRes.setSecret(userPrincipal.getAttributes().get("secret").toString());
        return userRes;
    }
}
