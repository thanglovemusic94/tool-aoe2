package com.backend.repository;

import com.backend.models.Review;
import com.backend.models.TYPE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "select count(r)>0 from Review r  where r.user_review_id = :user_review_id and r.user.id = :user_id and r.type = :type")
    Boolean existsByUser_review_id(Long user_review_id, Long user_id, TYPE type);

    @Query(value = "select r from Review r where r.user_review_id = :user_review_id and r.user.id = :user_id and r.type = :type")
    Review findReviewByUser_review_id(Long user_review_id, Long user_id, TYPE type);

    @Modifying
    @Transactional
    @Query(value = "delete from Review r where r.user.id = :user_id or r.user_review_id = :user_id")
    void deleteAllByUser_Id(Long user_id);
}
