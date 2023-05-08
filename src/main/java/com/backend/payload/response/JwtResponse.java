package com.backend.payload.response;

public class JwtResponse {
    private String token;
    private String inGame;

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
