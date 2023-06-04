package com.backend.controllers.auth;

import com.backend.models.Event;
import com.backend.models.Magt;
import com.backend.repository.EventRepository;
import com.backend.repository.MagtRepository;
import com.backend.repository.ReviewRepository;
import com.backend.repository.UserRepository;
import com.backend.security.services.AuthenticationFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/auth/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class EventController {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MagtRepository magtRepository;

    @Autowired
    private EventRepository eventRepository;


    @Autowired
    AuthenticationFacade authenticationFacade;

    @GetMapping("/event")
    public Page<Event> getAll(Pageable pageable){
        return eventRepository.findAll(pageable);
    }


    @PostMapping("/event")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public Event register(@RequestBody @Valid Event event) {
        return eventRepository.save(event);
    }




    @DeleteMapping("/event/{eventId}")
    @Transactional
    public void remove(@PathVariable long eventId) {
        var event = eventRepository.findById(eventId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "không tìm thấy event có id là " + eventId));
        eventRepository.delete(event);
    }

}
