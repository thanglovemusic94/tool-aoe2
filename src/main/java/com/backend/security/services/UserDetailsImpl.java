package com.backend.security.services;

import com.backend.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String inGame;

  @JsonIgnore
  private String password;

  private List<GrantedAuthority> authorities;

  public UserDetailsImpl(Long id, String inGame,  String password,
                         List<GrantedAuthority> authorities) {
    this.id = id;
    this.inGame = inGame;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserDetailsImpl build(User user) {
    List<GrantedAuthority> authorities = Collections.singletonList(user.getRoles());

    return new UserDetailsImpl(
        user.getId(), 
        user.getInGame(),
        user.getPassword(), 
        authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getId() {
    return id;
  }


  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return inGame;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
