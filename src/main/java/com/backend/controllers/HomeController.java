package com.backend.controllers;

import com.backend.dto.DiemTrungBinhView;
import com.backend.dto.TongDiemTBView;
import com.backend.models.User;
import com.backend.repository.ReviewRepository;
import com.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/home")
public class HomeController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewRepository reviewRepository;


    @GetMapping("")
    public Page<TongDiemTBView> getAll(Pageable pageable, @RequestParam(required = false) String xh) {
        Page<TongDiemTBView> page = reviewRepository.findAllHomePage(pageable);
        return page;
    }

    //  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/users")
    public Page<User> getAlUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @GetMapping("/diemtrungbinh")
    public List<DiemTrungBinhView> getDiemTrungBinh(@RequestParam(value = "user_review_id", required = false) Long user_review_id){
        List<DiemTrungBinhView> views = reviewRepository.findDiemTrungBinhById(user_review_id);
        return views;
    }

}





