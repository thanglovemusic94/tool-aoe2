package com.backend.payload.request;


import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
	@NotBlank
  	private String inGame;

	@NotBlank
	private String password;


	public String getInGame() {
		return inGame;
	}

	public void setInGame(String inGame) {
		this.inGame = inGame;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
