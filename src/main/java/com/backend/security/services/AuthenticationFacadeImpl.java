package com.backend.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public void setAuthentication(String inGame, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(inGame, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public UserDetailsImpl getAuthentication() {
        UserDetailsImpl userDetail = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetail;
    }

}
