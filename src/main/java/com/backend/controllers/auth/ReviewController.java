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

import java.util.*;


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
        List<Long> idUserHeSoX5 = new ArrayList<>(Arrays.asList(1l, 2l, 6l, 7l));
        for (int i = 0; i < dtoList.size(); i++) {
            float point = dtoList.get(i).getPoint();
            // kiểm tra nếu point >= 4 mới cho thay đổi dữ liệu
            if (point >= 4) {
                // kiểm tra user_review_id đã tồn tại trong review hay chua hay chưa, nếu chua thì add vao, neu co roi thi thay doi point thoi
                Boolean check = reviewRepository.existsByUser_review_id(dtoList.get(i).getUser_review_id(), user_id, dtoList.get(i).getType());
                if (check) { // co roi
                    Review review = reviewRepository.findReviewByUser_review_id(dtoList.get(i).getUser_review_id(), user_id, dtoList.get(i).getType());
                    review.setPoint(dtoList.get(i).getPoint());
                    reviewRepository.save(review);
                } else { // chua co
                    Review review = new Review();

                    if (idUserHeSoX5.contains(user_id)){
                        review.setHeSo(5);
                    }else review.setHeSo(1);

                    switch (dtoList.get(i).getType()) {
                        case DIEU_R -> {
                            review.setHeSoSolo(3);
                            review.setHeSo22(2);
                            review.setHeSo44(1);
                        }
                        case PHAT_TRIEN -> {
                            review.setHeSoSolo(2);
                            review.setHeSo22(2);
                            review.setHeSo44(3);
                        }
                        case THU_NHA -> {
                            review.setHeSoSolo(1);
                            review.setHeSo22(2);
                            review.setHeSo44(3);
                        }
                        case CHIEN_THUAT -> {
                            review.setHeSoSolo(1) ;
                            review.setHeSo22(1);
                            review.setHeSo44(2);
                        }
                        case DI_QUAN -> {
                            review.setHeSoSolo(2) ;
                            review.setHeSo22(3);
                            review.setHeSo44(2);
                        }
                        case EP_DOI -> {
                            review.setHeSoSolo(3);
                            review.setHeSo22(1);
                            review.setHeSo44(1);
                        }
                        case THAO_TAC_TAY -> {
                            review.setHeSoSolo(1);
                            review.setHeSo22(1);
                            review.setHeSo44(1);
                        }
                        default -> {
                            review.setHeSoSolo(1);
                            review.setHeSo22(1);
                            review.setHeSo44(1);
                        }
                    }

                    User user = userRepository.getById(user_id);
                    Long user_review_id = dtoList.get(i).getUser_review_id();
                    TYPE type = dtoList.get(i).getType();

                    review.setUser(user);
                    review.setUser_review_id(user_review_id);
                    review.setType(type);
                    review.setPoint(point);


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


        newListUser.sort(Comparator.comparing(UserDTO::getPoint));

        return newListUser;
    }
}
