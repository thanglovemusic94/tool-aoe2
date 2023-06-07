package com.backend.repository;

import com.backend.dto.DiemTrungBinhView;
import com.backend.dto.TongDiemTBView;
import com.backend.models.Review;
import com.backend.models.TYPE;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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
            "\t\t\t\t ROW_NUMBER() OVER(ORDER BY AVG(tb.diemtrungbinh) desc) as `rank` ,\n" +
            "        ROUND(AVG(tb.diemtrungbinh),\n" +
            "        1) as diemtrungbinh,\n" +
            "        tb.user_review_id as user_review_id,\n" +
            "        tb.inGame as inGame,\n" +
            "        tb.nickZalo as nickZalo,\n" +
            "        (   CASE     \n" +
            "           WHEN ROW_NUMBER() OVER(ORDER BY AVG(tb.diemtrungbinh) desc) <=16 THEN 'A'     \n" +
            "           WHEN 16 <ROW_NUMBER() OVER(ORDER BY AVG(tb.diemtrungbinh) desc) && ROW_NUMBER() OVER(ORDER BY AVG(tb.diemtrungbinh) desc) <=32 THEN 'B'     \n" +
            "           WHEN 32 < ROW_NUMBER() OVER(ORDER BY AVG(tb.diemtrungbinh) desc) && ROW_NUMBER() OVER(ORDER BY AVG(tb.diemtrungbinh) desc) <=48 THEN 'C'     \n" +
            "           WHEN 48 <ROW_NUMBER() OVER(ORDER BY AVG(tb.diemtrungbinh) desc)  THEN 'D'     \n" +
            "           ELSE 'CHƯA CÓ HẠNG'    \n" +
            "        END  ) as hang \n" +
            "    FROM\n" +
            "        (     SELECT\n" +
            "            rank() over(order by AVG(r.point) DESC),\n" +
            "            ROUND(sum(r.he_so*r.point)/sum(r.he_so), 1) as diemtrungbinh ,\n" +
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
            "            sum(r.he_so*r.point)/sum(r.he_so) DESC  ) as tb \n" +
            "        GROUP BY\n" +
            "            tb.user_review_id  \n" +
            "        ORDER BY\n" +
            "            AVG(tb.diemtrungbinh) DESC ";
    @Query(value = query2, countQuery = query2, nativeQuery = true)
    Page<TongDiemTBView> findAllHomePage(Pageable pageable);

    String querySolo = "SELECT\t ROW_NUMBER() OVER(ORDER BY sum(tb.diemtrungbinhSolo)/sum(he_so_solo) desc) as rank ,    \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t ROUND(sum(tb.diemtrungbinhSolo)/sum(he_so_solo), 1) as diemtrungbinh,      \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t tb.user_review_id as user_review_id,    \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t tb.inGame as inGame,    \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t tb.nickZalo as nickZalo,    \n" +
            "\t\t\t\t\t\t\t\t\t\t\t (   \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tCASE         \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN ROW_NUMBER() OVER(ORDER BY ROUND(sum(tb.diemtrungbinhSolo)/sum(he_so_solo), 2) desc) <=16 THEN 'A' \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN 16 < ROW_NUMBER() OVER(ORDER BY ROUND(sum(tb.diemtrungbinhSolo)/sum(he_so_solo), 2) desc) && \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tROW_NUMBER() OVER(ORDER BY ROUND(sum(tb.diemtrungbinhSolo)/sum(he_so_solo), 2) desc) <=32 THEN 'B'\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN 32 < ROW_NUMBER() OVER(ORDER BY ROUND(sum(tb.diemtrungbinhSolo)/sum(he_so_solo), 2) desc) && \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tROW_NUMBER() OVER(ORDER BY ROUND(sum(tb.diemtrungbinhSolo)/sum(he_so_solo), 2) desc) <=48 THEN 'C'\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN 48 <ROW_NUMBER() OVER(ORDER BY ROUND(sum(tb.diemtrungbinhSolo)/sum(he_so_solo), 2) desc)  THEN 'D'\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tELSE 'CHƯA CÓ HẠNG'        \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tEND  ) as hang     \n" +
            "\t\t\t\t\t\t\t\t\t FROM    \n" +
            "\t\t\t\t\t\t\t\t\t\t\t (    \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tSELECT \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t ROUND((sum(r.he_so*r.point*r.he_so_solo)/sum(r.he_so*he_so_solo))*he_so_solo, 2) as diemtrungbinhSolo,\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t r.type as type,    \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t r.user_review_id,\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t r.he_so_solo as he_so_solo,\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t r.point,\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t(SELECT in_game  FROM users u  WHERE  u.id = r.user_review_id) as inGame,   \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t(SELECT  nick_zalo FROM   users u WHERE  u.id = r.user_review_id) as nickZalo              \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tFROM  review r JOIN  users u on u.id = r.user_id  \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\tGROUP BY   type , r.user_review_id             \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t) as tb   \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tGROUP BY tb.user_review_id \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tORDER BY sum(tb.diemtrungbinhSolo)/sum(he_so_solo) DESC";

    @Query(value = querySolo, countQuery = querySolo, nativeQuery = true)
    Page<TongDiemTBView> findAllHomePageSolo(Pageable pageable);


    String query22 = "\t\t\t\t\t\t\t\t\tSELECT\t ROW_NUMBER() OVER(ORDER BY sum(tb.diemtrungbinh22) desc) as rank ,    \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t ROUND(sum(tb.diemtrungbinh22)/sum(he_so22), 1) as diemtrungbinh,      \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t tb.user_review_id as user_review_id,    \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t tb.inGame as inGame,    \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t tb.nickZalo as nickZalo,    \n" +
            "\t\t\t\t\t\t\t\t\t\t\t (   \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tCASE         \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN ROW_NUMBER() OVER(ORDER BY ROUND(sum(tb.diemtrungbinh22)/sum(he_so22), 2) desc) <=16 THEN 'A' \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN 16 < ROW_NUMBER() OVER(ORDER BY ROUND(sum(tb.diemtrungbinh22)/sum(he_so22), 2) desc) && \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tROW_NUMBER() OVER(ORDER BY ROUND(sum(tb.diemtrungbinh22)/sum(he_so22), 2) desc) <=32 THEN 'B'\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN 32 < ROW_NUMBER() OVER(ORDER BY ROUND(sum(tb.diemtrungbinh22)/sum(he_so22), 2) desc) && \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tROW_NUMBER() OVER(ORDER BY ROUND(sum(tb.diemtrungbinh22)/sum(he_so22), 2) desc) <=48 THEN 'C'\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN 48 <ROW_NUMBER() OVER(ORDER BY ROUND(sum(tb.diemtrungbinh22)/sum(he_so22), 2) desc)  THEN 'D'\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tELSE 'CHƯA CÓ HẠNG'        \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tEND  ) as hang     \n" +
            "\t\t\t\t\t\t\t\t\t FROM    \n" +
            "\t\t\t\t\t\t\t\t\t\t\t (    \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tSELECT \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t (sum(r.he_so*r.point*r.he_so22)/sum(r.he_so*he_so22))*he_so22 as diemtrungbinh22,\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t r.type as type,    \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t r.user_review_id,\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t r.he_so22 as he_so22,\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t r.point,\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t(SELECT in_game  FROM users u  WHERE  u.id = r.user_review_id) as inGame,   \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t(SELECT  nick_zalo FROM   users u WHERE  u.id = r.user_review_id) as nickZalo              \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tFROM  review r JOIN  users u on u.id = r.user_id  \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\tGROUP BY   type , r.user_review_id             \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t) as tb   \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tGROUP BY tb.user_review_id \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tORDER BY sum(tb.diemtrungbinh22)/sum(he_so22) DESC ";
    @Query(value = query22, countQuery = query22, nativeQuery = true)
    Page<TongDiemTBView> findAllHomePage22(Pageable pageable);



    String query44 = " SELECT\t ROW_NUMBER() OVER(ORDER BY ROUND(sum(tb.diemtrungbinh44)/sum(he_so44), 2) desc) as rank ,    \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t ROUND(sum(tb.diemtrungbinh44)/sum(he_so44), 1) as diemtrungbinh,      \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t tb.user_review_id as user_review_id,    \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t tb.inGame as inGame,    \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t tb.nickZalo as nickZalo,    \n" +
            "\t\t\t\t\t\t\t\t\t\t\t (   \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tCASE         \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN ROW_NUMBER() OVER(ORDER BY ROUND(sum(tb.diemtrungbinh44)/sum(he_so44), 2) desc) <=16 THEN 'A' \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN 16 < ROW_NUMBER() OVER(ORDER BY ROUND(sum(tb.diemtrungbinh44)/sum(he_so44), 2) desc) && \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tROW_NUMBER() OVER(ORDER BY ROUND(sum(tb.diemtrungbinh44)/sum(he_so44), 2) desc) <=32 THEN 'B'\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN 32 < ROW_NUMBER() OVER(ORDER BY ROUND(sum(tb.diemtrungbinh44)/sum(he_so44), 2) desc) && \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tROW_NUMBER() OVER(ORDER BY ROUND(sum(tb.diemtrungbinh44)/sum(he_so44), 2) desc) <=48 THEN 'C'\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN 48 <ROW_NUMBER() OVER(ORDER BY ROUND(sum(tb.diemtrungbinh44)/sum(he_so44), 2) desc)  THEN 'D'\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tELSE 'CHƯA CÓ HẠNG'        \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tEND  ) as hang     \n" +
            "\t\t\t\t\t\t\t\t\t FROM    \n" +
            "\t\t\t\t\t\t\t\t\t\t\t (    \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tSELECT \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t ROUND((sum(r.he_so*r.point*r.he_so44)/sum(r.he_so*he_so44))*he_so44, 2) as diemtrungbinh44,\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t r.type as type,    \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t r.user_review_id,\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t r.he_so44 as he_so44,\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t r.point,\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t(SELECT in_game  FROM users u  WHERE  u.id = r.user_review_id) as inGame,   \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t(SELECT  nick_zalo FROM   users u WHERE  u.id = r.user_review_id) as nickZalo              \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tFROM  review r JOIN  users u on u.id = r.user_id  \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\tGROUP BY   type , r.user_review_id             \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t) as tb   \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tGROUP BY tb.user_review_id \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tORDER BY sum(tb.diemtrungbinh44)/sum(he_so44) DESC ";
    @Query(value = query44, countQuery = query44, nativeQuery = true)
    Page<TongDiemTBView> findAllHomePage44(Pageable pageable);


    String query3 = "\n" +
            "SELECT count(r.id) as soNguoiDanhGia, " +
            "rank() over(order by AVG(r.point) DESC) as `rank`,  \n" +
            "ROUND(sum(r.he_so*r.point)/sum(r.he_so), 1) as diemtrungbinh , \n" +
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
            "\tROUND(sum(r.he_so*r.point)/sum(r.he_so), 1) as diemtrungbinh , \n" +
            "r.user_review_id as user_review_id, \n" +
            "\tr.type as `type` \n" +
            "\tFROM review r JOIN users u on u.id = r.user_id WHERE (r.user_review_id = :user_review_id or :user_review_id is null) \n" +
            "GROUP BY type, r.user_review_id  ORDER BY sum(r.he_so*r.point)/sum(r.he_so) DESC ";

    @Query(value = query4, countQuery = query4, nativeQuery = true)
    List<DiemTrungBinhView> findDiemTrungBinhById(@Param(value = "user_review_id") Long user_review_id);

    @Query(value = query4, countQuery = query4, nativeQuery = true)
    List<Tuple> findDiemTrungBinhDTOById(@Param(value = "user_review_id") Long user_review_id);
    @Query(value = "SELECT count(tb.review_id) as soNguoiChamDiem FROM (SELECT r.id as review_id  FROM review r  WHERE user_review_id = :user_review_id GROUP BY user_review_id, user_id) tb", nativeQuery = true)
    Long soNguoiChamDiemById(BigInteger user_review_id);
}
