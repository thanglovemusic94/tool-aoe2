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
    public Page<UserReviewDTO> getAll(Pageable pageable, @RequestParam(required = false) String xh) {


        Page<Tuple> tuplePage = reviewRepository.findAllHomePage(pageable);

        Page<UserReviewDTO> page = tuplePage.map(new Function<Tuple, UserReviewDTO>() {
                                                     @Override
                                                     public UserReviewDTO apply(Tuple tuple) {
                                                         UserReviewDTO dto = new UserReviewDTO();
                                                         dto.setRank((BigInteger) tuple.get("rank"));
                                                         dto.setHang((String) tuple.get("hang"));

                                                         dto.setUser_review_id((BigInteger) tuple.get("user_review_id"));
                                                         dto.setNickZalo((String) tuple.get("nickZalo"));
                                                         dto.setInGame((String) tuple.get("inGame"));

                                                         Long soNguoiChamDiem = reviewRepository.soNguoiChamDiemById((BigInteger) tuple.get("user_review_id"));
                                                         dto.setSoNguoiChamDiem(soNguoiChamDiem);

//                                                         WHEN r.type = 'PHAT_TRIEN' THEN ROUND(AVG(r.point),1) * 3
//                                                         WHEN r.type = 'EP_DOI' THEN ROUND(AVG(r.point),1) * 1
//                                                         WHEN r.type = 'DIEU_R' THEN ROUND(AVG(r.point),1) * 1
//                                                         WHEN r.type = 'THAO_TAC_TAY' THEN ROUND(AVG(r.point),1) * 1
//                                                         WHEN r.type = 'DI_QUAN' THEN ROUND(AVG(r.point),1) * 2
//                                                         WHEN r.type = 'CHIEN_THUAT' THEN ROUND(AVG(r.point),1) * 2
//                                                         WHEN r.type = 'THU_NHA' THEN ROUND(AVG(r.point),1) * 3

                                                         if (xh.equals("xh44")){
                                                             List<Tuple> views =   reviewRepository.findDiemTrungBinhDTOById(dto.getUser_review_id().longValue());
                                                             double diemtb = 0;
                                                             for (int i = 0; i < views.size(); i++) {
                                                                 if (views.get(i).get(3).equals("THAO_TAC_TAY")){
                                                                     diemtb += (double) views.get(i).get(1) * 1;
                                                                 }else if (views.get(i).get(3).equals("PHAT_TRIEN")){
                                                                     diemtb += (double) views.get(i).get(1) * 3;
                                                                 }if (views.get(i).get(3).equals("EP_DOI")){
                                                                     diemtb += (double) views.get(i).get(1) * 1;
                                                                 }if (views.get(i).get(3).equals("DIEU_R")){
                                                                     diemtb += (double) views.get(i).get(1) * 1;
                                                                 }if (views.get(i).get(3).equals("DI_QUAN")){
                                                                     diemtb += (double) views.get(i).get(1) * 2;
                                                                 }if (views.get(i).get(3).equals("CHIEN_THUAT")){
                                                                     diemtb += (double) views.get(i).get(1) * 2;
                                                                 }if (views.get(i).get(3).equals("THU_NHA")){
                                                                     diemtb += (double) views.get(i).get(1) * 3;
                                                                 }

                                                             }
                                                             diemtb = diemtb/13;
                                                             diemtb = Math.ceil(diemtb * 10) / 10;
                                                             dto.setDiemtrungbinh(diemtb);
                                                         }else if (xh.equals("xh22")){
                                                             List<Tuple> views =   reviewRepository.findDiemTrungBinhDTOById(dto.getUser_review_id().longValue());
                                                             double diemtb = 0;
                                                             for (int i = 0; i < views.size(); i++) {
                                                                 if (views.get(i).get(3).equals("THAO_TAC_TAY")){
                                                                     diemtb += (double) views.get(i).get(1) * 1;
                                                                 }else if (views.get(i).get(3).equals("PHAT_TRIEN")){
                                                                     diemtb += (double) views.get(i).get(1) * 2;
                                                                 }if (views.get(i).get(3).equals("EP_DOI")){
                                                                     diemtb += (double) views.get(i).get(1) * 1;
                                                                 }if (views.get(i).get(3).equals("DIEU_R")){
                                                                     diemtb += (double) views.get(i).get(1) * 2;
                                                                 }if (views.get(i).get(3).equals("DI_QUAN")){
                                                                     diemtb += (double) views.get(i).get(1) * 3;
                                                                 }if (views.get(i).get(3).equals("CHIEN_THUAT")){
                                                                     diemtb += (double) views.get(i).get(1) * 1;
                                                                 }if (views.get(i).get(3).equals("THU_NHA")){
                                                                     diemtb += (double) views.get(i).get(1) * 2;
                                                                 }

                                                             }
                                                             diemtb = diemtb/12;
                                                             diemtb = Math.ceil(diemtb * 10) / 10;
                                                             dto.setDiemtrungbinh(diemtb);
                                                         }
                                                         else if (xh.equals("null") ){
                                                             dto.setDiemtrungbinh((Double) tuple.get("diemtrungbinh"));
                                                         }
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
                dto.setDiemtrungbinh((Double) tuple.get("diemtrungbinh"));
                dto.setSoNguoiDanhGia((BigInteger) tuple.get("soNguoiDanhGia"));
                dto.setUser_review_id((BigInteger) tuple.get("user_review_id"));
                return dto;
            }
        }).toList();

        return dtos;
    }

}





