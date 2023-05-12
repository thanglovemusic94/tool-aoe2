package com.backend.controllers;

import com.backend.dto.DiemTrungBinhDTO;
import com.backend.dto.DiemTrungBinhView;
import com.backend.dto.DiemTrungBinhViewDTO;
import com.backend.dto.UserReviewDTO;
import com.backend.models.TYPE;
import com.backend.models.User;
import com.backend.repository.ReviewRepository;
import com.backend.repository.UserRepository;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/api/home")
public class HomeController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewRepository reviewRepository;


    @GetMapping("")
    public Page<UserReviewDTO> getAll(Pageable pageable) {


        Page<Tuple> tuplePage = reviewRepository.findAllHomePage(pageable);

        Page<UserReviewDTO> page = tuplePage.map(new Function<Tuple, UserReviewDTO>() {
                                                     @Override
                                                     public UserReviewDTO apply(Tuple tuple) {
                                                         UserReviewDTO dto = new UserReviewDTO();
                                                         dto.setRank((BigInteger) tuple.get("rank"));
                                                         dto.setHang((String) tuple.get("hang"));
                                                         dto.setDiemtrungbinh((BigDecimal) tuple.get("diemtrungbinh"));
                                                         dto.setUser_review_id((BigInteger) tuple.get("user_review_id"));
                                                         dto.setNickZalo((String) tuple.get("nickZalo"));
                                                         dto.setInGame((String) tuple.get("inGame"));
                                                         return dto;
                                                     }
                                                 }

        );
        return page;
    }

    //  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/users")
    public Page<User> getAlUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

//    @GetMapping("/diemtrungbinh")
//    public List<DiemTrungBinhView> getDiemTrungBinh(@RequestParam(value = "user_review_id", required = false) Long user_review_id){
//        List<DiemTrungBinhView> views = reviewRepository.findDiemTrungBinhById(user_review_id);
//        return views;
//    }

    @GetMapping("/diemtrungbinh")
    public List<DiemTrungBinhViewDTO> getDiemTrungBinh(@RequestParam(value = "user_review_id", required = false) Long user_review_id){
        List<Tuple> views = reviewRepository.findDiemTrungBinhDTOById(user_review_id);
        List<DiemTrungBinhViewDTO> dtos  = views.stream().map(new Function<Tuple, DiemTrungBinhViewDTO>() {
            @Override
            public DiemTrungBinhViewDTO apply(Tuple tuple) {
                DiemTrungBinhViewDTO dto = new DiemTrungBinhViewDTO();
                dto.setType((String) tuple.get("type"));
                dto.setDiemtrungbinh((BigDecimal) tuple.get("diemtrungbinh"));
                dto.setSoNguoiDanhGia((BigInteger) tuple.get("soNguoiDanhGia"));
                dto.setUser_review_id((BigInteger) tuple.get("user_review_id"));
                return dto;
            }
        }).toList();

        return dtos;
    }
//    @GetMapping("/diemtrungbinh2")
//    public Page<DiemTrungBinhDTO> getAlDiemTrungBinh(@RequestParam(required = false, defaultValue = "null") String type, Pageable pageable) {
//        Page<Tuple> tuplePage = reviewRepository.findAllDiemTrungBinh(type, pageable);
//
//        Page<DiemTrungBinhDTO> page = tuplePage.map(new Function<Tuple, DiemTrungBinhDTO>() {
//                                                        @Override
//                                                        public DiemTrungBinhDTO apply(Tuple tuple) {
//                                                            DiemTrungBinhDTO dto = new DiemTrungBinhDTO();
//                                                            dto.setSoNguoiDanhGia(tuple.get(0, BigInteger.class));
//                                                            dto.setHang(tuple.get(1, BigInteger.class));
//                                                            dto.setDiemtrungbinh((BigDecimal) tuple.get("diemtrungbinh"));
//                                                            dto.setUser_review_id(tuple.get(3, BigInteger.class));
//                                                            dto.setNickZalo(tuple.get(4, String.class));
//                                                            dto.setInGame(tuple.get(5, String.class));
//                                                            return dto;
//                                                        }
//                                                    }
//
//        );
//        return page;
//    }
}





