package com.backend.payload.request;

import java.util.Set;

import javax.validation.constraints.*;

public class SignupRequest {

  private String hovaten;
  @NotBlank
  private String inGame;


  private String nickZalo;


  private String sdt;


  private String nickFb;

  @NotBlank
  private String password;

  private String role;
  private String maGT;

  public String getMaGT() {
    return maGT;
  }

  public void setMaGT(String maGT) {
    this.maGT = maGT;
  }

  public String getHovaten() {
    return hovaten;
  }

  public void setHovaten(String hovaten) {
    this.hovaten = hovaten;
  }

  public String getInGame() {
    return inGame;
  }

  public void setInGame(String inGame) {
    this.inGame = inGame;
  }

  public String getNickZalo() {
    return nickZalo;
  }

  public void setNickZalo(String nickZalo) {
    this.nickZalo = nickZalo;
  }

  public String getSdt() {
    return sdt;
  }

  public void setSdt(String sdt) {
    this.sdt = sdt;
  }

  public String getNickFb() {
    return nickFb;
  }

  public void setNickFb(String nickFb) {
    this.nickFb = nickFb;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
