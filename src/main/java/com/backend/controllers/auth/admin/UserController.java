package com.backend.controllers.auth.admin;

import com.backend.models.Magt;
import com.backend.models.User;
import com.backend.repository.MagtRepository;
import com.backend.repository.ReviewRepository;
import com.backend.repository.UserRepository;
import com.backend.security.services.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MagtRepository magtRepository;

    @Autowired
    AuthenticationFacade authenticationFacade;

    @GetMapping("/users")
    public Page<User> create(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    @DeleteMapping("/user/{in_game}")
    public void create(@PathVariable String in_game){
        User user = userRepository.findByinGame(in_game).get();
        reviewRepository.deleteAllByUser_Id(user.getId());
        magtRepository.deleteAllByUser_Id(user.getId());
        userRepository.delete(user);
    }
}
