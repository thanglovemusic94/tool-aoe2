package com.backend.controllers;

import com.backend.dto.DiemTrungBinhDTO;
import com.backend.dto.UserReviewDTO;
import com.backend.models.TYPE;
import com.backend.models.User;
import com.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/home")
public class HomeController {
  @Autowired
  UserRepository userRepository;

  @GetMapping("")
  public Page<UserReviewDTO> getAll(Pageable pageable){
    return userRepository.findAllHomePage(pageable);
  }

  //  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @GetMapping("/users")
  public Page<User> getAlUser(Pageable pageable){
    return userRepository.findAll(pageable);
  }

  @GetMapping("/diemtrungbinh")
  public Page<DiemTrungBinhDTO> getAlDiemTrungBinh(@RequestParam(required = false) String type, Pageable pageable){
    return userRepository.findAllDiemTrungBinh(type, pageable);
  }
}
