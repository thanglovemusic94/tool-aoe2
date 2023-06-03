package com.backend.repository;

import java.util.List;
import java.util.Optional;

import com.backend.dto.UserDTO;
import com.backend.models.TYPE;
import com.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByinGame(String inGame);

    @Query(value = "select new com.backend.dto.UserDTO(u.id, u.inGame) from User u where u.id <> :user_id")
    List<UserDTO> findAllUserCustom(Long user_id);

    @Query(value = "select new com.backend.dto.UserDTO(r.user.id, r.user_review_id, (SELECT u.inGame  FROM User u WHERE r.user_review_id = u.id), r.type, r.point) " +
            "from User u join Review r on  u.id = r.user.id where r.user.id = :user_id and r.type = :type")
    List<UserDTO> findAllUserReview(Long user_id, @Param("type") TYPE type);

    @Query(value = "select count(u)>0 from User u where lower(u.inGame) = :inGame")
    Boolean existsByinGame(String inGame);


    @Query(value = "select u.inGame from User u where u.id = :user_review_id")
    String findInGame(@Param("user_review_id") Long user_review_id);



}
