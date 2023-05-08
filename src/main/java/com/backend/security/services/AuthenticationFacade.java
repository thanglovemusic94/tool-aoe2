package com.backend.security.services;


import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationFacade {

    void setAuthentication(String inGame, String password);

    UserDetails getAuthentication();

}
