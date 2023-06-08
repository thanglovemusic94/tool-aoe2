package com.backend.controllers.auth.admin;

import com.backend.models.Magt;
import com.backend.models.StatusMagt;
import com.backend.repository.MagtRepository;
import com.backend.repository.ReviewRepository;
import com.backend.repository.UserRepository;
import com.backend.security.services.AuthenticationFacade;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/auth/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MagtRepository magtRepository;

    @Autowired
    AuthenticationFacade authenticationFacade;

    @PostMapping("/magt")
    public List<Magt> create(){

        List<Magt> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            //String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
            //https://stackoverflow.com/questions/31260512/generate-a-secure-random-password-in-java-with-minimum-special-character-require
            char[] possibleCharacters = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789")).toCharArray();
            String str = RandomStringUtils.random( 6, 0, possibleCharacters.length-1, false, false, possibleCharacters, new SecureRandom() );
            boolean check = magtRepository.existsByCode(str);
            while (check){
                str = RandomStringUtils.random( 6, 0, possibleCharacters.length-1, false, false, possibleCharacters, new SecureRandom() );
            }
            Magt magt1 = new Magt(str);
            list.add(magt1);
            magtRepository.save(magt1);
        }
        return list;
    }

    @GetMapping("/magt")
    public Page<Magt> create(Pageable pageable){
        return magtRepository.findAll(pageable);
    }
}
