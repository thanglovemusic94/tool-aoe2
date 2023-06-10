package com.backend.controllers.auth.admin;

import com.backend.models.Event;
import com.backend.models.NhaTaiTro;
import com.backend.repository.*;
import com.backend.security.services.AuthenticationFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class NhaTaiTroController {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MagtRepository magtRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private NhaTaiTroRepository nhaTaiTroRepository;


    @Autowired
    AuthenticationFacade authenticationFacade;

    @PostMapping("/nhataitro")
    @Transactional
    public NhaTaiTro register(@RequestBody @Valid NhaTaiTro nhaTaiTro) {
        return nhaTaiTroRepository.save(nhaTaiTro);
    }


}
