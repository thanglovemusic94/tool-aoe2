package com.backend.repository;

import java.util.List;
import java.util.Optional;

import com.backend.dto.DiemTrungBinhDTO;
import com.backend.dto.UserDTO;
import com.backend.dto.UserDTOProjection;
import com.backend.dto.UserReviewDTO;
import com.backend.models.TYPE;
import com.backend.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByinGame(String inGame);


    //@Query(value = "SELECT new com.backend.dto.UserDT from users u", nativeQuery = true)
    @Query(value = "select new com.backend.dto.UserDTO(u.id, u.inGame) from User u where u.id <> :user_id")
    List<UserDTO> findAllUserCustom(Long user_id);

    @Query(value = "select new com.backend.dto.UserDTO(r.user.id, r.user_review_id, (SELECT u.inGame  FROM User u WHERE r.user_review_id = u.id), r.type, r.point) " +
            "from User u join Review r on  u.id = r.user.id where r.user.id = :user_id and r.type = :type")
//@Query(value = "SELECT r.user_id as user_id, r.user_review_id as user_review_id, (SELECT u.in_game  FROM users u WHERE r.user_review_id = u.id) as in_game , r.type as type, r.point as point " +
//        "FROM users u JOIN review r on u.id = r.user_id WHERE u.id = 1 and r.type = \"THAO_TAC_TAY\"", nativeQuery = true)
    List<UserDTO> findAllUserReview(Long user_id, @Param("type") TYPE type);

    @Query(value = "select count(u)>0 from User u where lower(u.inGame) = :inGame")
    Boolean existsByinGame(String inGame);


    @Query(value = "select u.inGame from User u where u.id = :user_review_id")
    String findInGame(@Param("user_review_id") Long user_review_id);


    String query2 = "SELECT rank() over(order by AVG(tb.diemtrungbinh) DESC) as rank, \n" +
            "ROUND(AVG(tb.diemtrungbinh),1) as diemtrungbinh,\n" +
            " tb.user_review_id as user_review_id, \n" +
            " tb.inGame as inGame,\n" +
            " tb.nickZalo as nickZalo,\n" +
            " (\n" +
            "\t CASE \n" +
            "\t\t\tWHEN ROUND(AVG(tb.diemtrungbinh),1) >= 9 THEN 'A' \n" +
            "\t\t\tWHEN ROUND(AVG(tb.diemtrungbinh),1) >= 7 && ROUND(AVG(tb.diemtrungbinh),1) < 9 THEN 'B' \n" +
            "\t\t\tWHEN ROUND(AVG(tb.diemtrungbinh),1) >= 5 && ROUND(AVG(tb.diemtrungbinh),1) < 7  THEN 'C'\n" +
            "\t\t\tWHEN ROUND(AVG(tb.diemtrungbinh),1) >= 3 && ROUND(AVG(tb.diemtrungbinh),1) < 5  THEN 'D'\n" +
            "\t\t\tELSE 'CHƯA CÓ HẠNG'\t\n" +
            "\t\tEND \n" +
            ") as hang\n" +
            "FROM\n" +
            "\t(\n" +
            "\t\t\t\tSELECT \n" +
            "\t\t\t\t\trank() over(order by AVG(r.point) DESC) as rank, \n" +
            "\t\t\t\t\tROUND(AVG(r.point),1) as diemtrungbinh, \n" +
            "\t\t\t\t\tr.type, \n" +
            "\t\t\t\t\tr.user_review_id, \n" +
            "\t\t\t\t\t(SELECT in_game FROM users u WHERE u.id = r.user_review_id) as inGame,  \n" +
            "\t\t\t\t\t(SELECT nick_zalo FROM users u WHERE u.id = r.user_review_id) as nickZalo \n" +
            "\t\t\t\tFROM review r JOIN   users u on u.id = r.user_id\n" +
            "\t\t\t\tGROUP BY type, r.user_review_id  \n" +
            "\t\t\t\tORDER BY AVG(r.point) DESC\n" +
            "\t) as tb\n" +
            "GROUP BY tb.user_review_id  ORDER BY AVG(tb.diemtrungbinh) DESC ";
    @Query(value = query2, countQuery = query2, nativeQuery = true)
    Page<UserReviewDTO> findAllHomePage(Pageable pageable);


    String query3 = "\n" +
            "SELECT count(r.id) as soNguoiDanhGia, " +
            "rank() over(order by AVG(r.point) DESC) as rank,  \n" +
            "ROUND(AVG(r.point),1) as diemtrungbinh, \n" +
            "r.user_review_id as user_review_id, \n" +
            "(SELECT nick_zalo FROM users u WHERE u.id = r.user_review_id) as nickZalo ,\n" +
            "(SELECT in_game FROM users u WHERE u.id = r.user_review_id) as inGame \n" +
            "FROM review r JOIN users u on u.id = r.user_id\n" +
            "WHERE (:type is null or r.type = :type)  \n" +
            "GROUP BY r.type, r.user_review_id  ORDER BY AVG(r.point) DESC ";

    @Query(value = query3, countQuery = query3, nativeQuery = true)
    Page<DiemTrungBinhDTO> findAllDiemTrungBinh(@Param(value = "type") String type , Pageable pageable);
}
