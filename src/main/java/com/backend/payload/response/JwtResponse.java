package com.backend.payload.response;

import org.springframework.security.core.GrantedAuthority;

public class JwtResponse {
    private String token;
    private String inGame;
    private GrantedAuthority role;

    public JwtResponse(String token, GrantedAuthority role) {
        this.token = token;
        this.role = role;
    }

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public GrantedAuthority getRole() {
        return role;
    }

    public void setRole(GrantedAuthority role) {
        this.role = role;
    }
}
