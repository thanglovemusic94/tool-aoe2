package com.backend.controllers;

import com.backend.dto.DiemTrungBinhView;
import com.backend.dto.TongDiemTBView;
import com.backend.dto.UserEventView;
import com.backend.models.Event;
import com.backend.models.User;
import com.backend.models.UserEvent;
import com.backend.repository.EventRepository;
import com.backend.repository.ReviewRepository;
import com.backend.repository.UserEventRepository;
import com.backend.repository.UserRepository;
import com.backend.security.services.AuthenticationFacade;
import com.backend.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/home")
public class HomeController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    private EventRepository eventRepository;


    @Autowired
    private UserEventRepository userEventRepository;


    @Autowired
    AuthenticationFacade authenticationFacade;

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


    @GetMapping("/event-new")
    public Event getAllNew(){
        return eventRepository.findOneEventNew();
    }

    @PostMapping("/event-register/{event_code}")
    public UserEvent registerEventUser(@PathVariable String event_code){
        UserDetailsImpl user = (UserDetailsImpl) authenticationFacade.getAuthentication();
        Event event = eventRepository.findByEventCode(event_code);
        User user1 = userRepository.findById(user.getId()).get();
        UserEvent userEvent = new UserEvent();
        userEvent.setEvent(event);
        userEvent.setUser(user1);
        userEventRepository.save(userEvent);
        return userEvent;
    }

    @GetMapping("/event-register/{event_id}")
    public List<UserEventView> registerEventUser(@PathVariable Long event_id){
        return userEventRepository.findUserByEventId(event_id);
    }
}





