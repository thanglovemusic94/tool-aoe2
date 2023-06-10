package com.backend.controllers.auth.admin;

import com.backend.dto.TongTienView;
import com.backend.models.UserEvent;
import com.backend.repository.MagtRepository;
import com.backend.repository.ReviewRepository;
import com.backend.repository.UserEventRepository;
import com.backend.repository.UserRepository;
import com.backend.security.services.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserEventController {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MagtRepository magtRepository;

    @Autowired
    private UserEventRepository userEventRepository;

    @Autowired
    AuthenticationFacade authenticationFacade;

    @PostMapping("/userevent/{id}")
    public UserEvent addData(@RequestBody UserEvent userEvent, @PathVariable Long id){
        UserEvent userEvent1 = userEventRepository.findById(id).get();
        userEvent1.setStatusDongTien(userEvent.getStatusDongTien());
        userEvent1.setTienDong(userEvent.getTienDong());
        userEvent1.setTienHoTroGiai(userEvent.getTienHoTroGiai());
        userEventRepository.save(userEvent1);
        return userEvent1;
    }

    @DeleteMapping("/userevent/{id}")
    public UserEvent remove(@PathVariable Long id){
        UserEvent userEvent1 = userEventRepository.findById(id).get();
        userEventRepository.delete(userEvent1);
        return userEvent1;
    }

}
