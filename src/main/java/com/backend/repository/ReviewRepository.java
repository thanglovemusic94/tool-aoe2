package com.backend.repository;

import com.backend.dto.DiemTrungBinhView;
import com.backend.models.Review;
import com.backend.models.TYPE;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "select count(r)>0 from Review r  where r.user_review_id = :user_review_id and r.user.id = :user_id and r.type = :type")
    Boolean existsByUser_review_id(Long user_review_id, Long user_id, TYPE type);

    @Query(value = "select r from Review r where r.user_review_id = :user_review_id and r.user.id = :user_id and r.type = :type")
    Review findReviewByUser_review_id(Long user_review_id, Long user_id, TYPE type);

    @Modifying
    @Transactional
    @Query(value = "delete from Review r where r.user.id = :user_id or r.user_review_id = :user_id")
    void deleteAllByUser_Id(Long user_id);

    String query2 = "SELECT\n" +
            "\t\t\t\trank()  over(order by AVG(tb.diemtrungbinh) DESC) as `rank` ,\n" +
            "        ROUND(AVG(tb.diemtrungbinh),\n" +
            "        1) as diemtrungbinh,\n" +
            "        tb.user_review_id as user_review_id,\n" +
            "        tb.inGame as inGame,\n" +
            "        tb.nickZalo as nickZalo,\n" +
            "        (   CASE     \n" +
            "            WHEN ROUND(AVG(tb.diemtrungbinh),\n" +
            "            1) >= 9.5 THEN 'A+'     \n" +
            "            WHEN ROUND(AVG(tb.diemtrungbinh),\n" +
            "            1) >=8.8 && ROUND(AVG(tb.diemtrungbinh),\n" +
            "            1) < 9.5 THEN 'A'     \n" +
            "            WHEN ROUND(AVG(tb.diemtrungbinh),\n" +
            "            1) >= 8.6 && ROUND(AVG(tb.diemtrungbinh),\n" +
            "            1) < 8.8  THEN 'A-'    \n" +
            "            WHEN ROUND(AVG(tb.diemtrungbinh),\n" +
            "            1) >= 8.4 && ROUND(AVG(tb.diemtrungbinh),\n" +
            "            1) < 8.6  THEN 'B'    \n" +
            "            WHEN ROUND(AVG(tb.diemtrungbinh),\n" +
            "            1) >= 8.1 && ROUND(AVG(tb.diemtrungbinh),\n" +
            "            1) < 8.4  THEN 'B-'    \n" +
            "            WHEN ROUND(AVG(tb.diemtrungbinh),\n" +
            "            1) >= 7.8 && ROUND(AVG(tb.diemtrungbinh),\n" +
            "            1) < 8.1  THEN 'C'    \n" +
            "            WHEN ROUND(AVG(tb.diemtrungbinh),\n" +
            "            1) >= 7.5 && ROUND(AVG(tb.diemtrungbinh),\n" +
            "            1) < 7.8  THEN 'C-'    \n" +
            "           WHEN ROUND(AVG(tb.diemtrungbinh),1) >= 7 && ROUND(AVG(tb.diemtrungbinh),1) < 7.5  THEN 'D' \n"+
            "           WHEN ROUND(AVG(tb.diemtrungbinh),1) >= 4 && ROUND(AVG(tb.diemtrungbinh),1) < 7 THEN 'D-' \n"+
            "           ELSE 'CHƯA CÓ HẠNG'    \n" +
            "        END  ) as hang \n" +
            "    FROM\n" +
            "        (     SELECT\n" +
            "            rank() over(order by AVG(r.point) DESC),\n" +
            "            ROUND(AVG(r.point),\n" +
            "            2) as diemtrungbinh,\n" +
            "            r.type,\n" +
            "            r.user_review_id,\n" +
            "            (SELECT\n" +
            "                in_game \n" +
            "            FROM\n" +
            "                users u \n" +
            "            WHERE\n" +
            "                u.id = r.user_review_id) as inGame,\n" +
            "            (SELECT\n" +
            "                nick_zalo \n" +
            "            FROM\n" +
            "                users u \n" +
            "            WHERE\n" +
            "                u.id = r.user_review_id) as nickZalo      \n" +
            "        FROM\n" +
            "            review r \n" +
            "        JOIN\n" +
            "            users u \n" +
            "                on u.id = r.user_id     \n" +
            "        GROUP BY\n" +
            "            type,\n" +
            "            r.user_review_id       \n" +
            "        ORDER BY\n" +
            "            AVG(r.point) DESC  ) as tb \n" +
            "        GROUP BY\n" +
            "            tb.user_review_id  \n" +
            "        ORDER BY\n" +
            "            AVG(tb.diemtrungbinh) DESC ";
    @Query(value = query2, countQuery = query2, nativeQuery = true)
    Page<Tuple> findAllHomePage(Pageable pageable);


    String query3 = "\n" +
            "SELECT count(r.id) as soNguoiDanhGia, " +
            "rank() over(order by AVG(r.point) DESC) as `rank`,  \n" +
            "ROUND(AVG(r.point),1) as diemtrungbinh, \n" +
            "r.user_review_id as user_review_id, \n" +
            "(SELECT nick_zalo FROM users u WHERE u.id = r.user_review_id) as nickZalo ,\n" +
            "(SELECT in_game FROM users u WHERE u.id = r.user_review_id) as inGame \n" +
            "FROM review r JOIN users u on u.id = r.user_id\n" +
            "WHERE (r.type = :type or :type is null)  \n" +
            "GROUP BY r.type, r.user_review_id  ORDER BY AVG(r.point) DESC ";

    @Query(value = query3, countQuery = query3, nativeQuery = true)
    Page<Tuple> findAllDiemTrungBinh(@Param(value = "type") String type , Pageable pageable);

    String query4 = "SELECT \n" +
            "\tcount(r.id) as soNguoiDanhGia,\n" +
            "\tROUND(AVG(r.point),1) as diemtrungbinh, \n" +
            "r.user_review_id as user_review_id, \n" +
            "\tr.type as `type` \n" +
            "\tFROM review r JOIN users u on u.id = r.user_id WHERE (r.user_review_id = :user_review_id or :user_review_id is null) \n" +
            "GROUP BY type, r.user_review_id  ORDER BY AVG(r.point) DESC ";

    @Query(value = query4, countQuery = query4, nativeQuery = true)
    List<DiemTrungBinhView> findDiemTrungBinhById(@Param(value = "user_review_id") Long user_review_id);

    @Query(value = query4, countQuery = query4, nativeQuery = true)
    List<Tuple> findDiemTrungBinhDTOById(@Param(value = "user_review_id") Long user_review_id);
    @Query(value = "SELECT count(tb.review_id) as soNguoiChamDiem FROM (SELECT r.id as review_id  FROM review r  WHERE user_review_id = :user_review_id GROUP BY user_review_id, user_id) tb", nativeQuery = true)
    Long soNguoiChamDiemById(BigInteger user_review_id);
}
