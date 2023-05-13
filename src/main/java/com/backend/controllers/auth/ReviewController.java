package com.backend.controllers.auth;

import com.backend.dto.ReviewCreateDTO;
import com.backend.dto.UserDTO;
import com.backend.models.Review;
import com.backend.models.TYPE;
import com.backend.models.User;
import com.backend.repository.ReviewRepository;
import com.backend.repository.UserRepository;
import com.backend.security.services.AuthenticationFacade;
import com.backend.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@RestController
@RequestMapping("/api/auth")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationFacade authenticationFacade;


    @PostMapping("/review")
    public void createReview(@RequestBody List<ReviewCreateDTO> dtoList) {
        UserDetailsImpl user1 = (UserDetailsImpl) authenticationFacade.getAuthentication();
        Long user_id = user1.getId();
        for (int i = 0; i < dtoList.size(); i++) {
            float point = dtoList.get(i).getPoint();
            // kiểm tra nếu point >= 4 mới cho thay đổi dữ liệu
            if (point >= 4) {
                // kiểm tra user_review_id đã tồn tại trong review hay chua hay chưa, nếu chua thì add vao, neu co roi thi thay doi point thoi
                Boolean check = reviewRepository.existsByUser_review_id(dtoList.get(i).getUser_review_id(), user_id, dtoList.get(i).getType());
                if (check) {
                    Review review = reviewRepository.findReviewByUser_review_id(dtoList.get(i).getUser_review_id(), user_id, dtoList.get(i).getType());
                    review.setPoint(dtoList.get(i).getPoint());
                    reviewRepository.save(review);
                } else {
                    User user = userRepository.getById(user_id);
                    Long user_review_id = dtoList.get(i).getUser_review_id();
                    TYPE type = dtoList.get(i).getType();
                    Review review = new Review(point, user_review_id, type, user);
                    reviewRepository.save(review);
                }
            }



        }
    }

    @GetMapping("/user")
    public List<UserDTO> reviewUser(@RequestParam TYPE type) {
        UserDetailsImpl user1 = (UserDetailsImpl) authenticationFacade.getAuthentication();
        Long user_id = user1.getId();
        List<UserDTO> userReview = userRepository.findAllUserReview(user_id, type);
        List<UserDTO> user = userRepository.findAllUserCustom(user_id);

        List<UserDTO> newListUser = new ArrayList<>();
        newListUser.addAll(userReview);

        for (int i = 0; i < user.size(); i++) {
            UserDTO dto = new UserDTO();
            boolean check = newListUser.contains(user.get(i));
            if (check == false) {
                dto.setUser_id(user_id);
                dto.setUser_review_id(user.get(i).getUser_id());
                dto.setType(type);
                dto.setPoint(user.get(i).getPoint());
                dto.setInGame(user.get(i).getInGame());
                newListUser.add(dto);
            }
        }


        newListUser.sort(Comparator.comparing(UserDTO::getPoint).reversed());
        //newListUser.sort(Comparator.comparingDouble(UserDTO::getPoint).reversed());

        return newListUser;
    }
}
