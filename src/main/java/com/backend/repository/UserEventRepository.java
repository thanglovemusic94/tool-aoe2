package com.backend.repository;

import com.backend.dto.UserEventView;
import com.backend.models.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserEventRepository  extends JpaRepository<UserEvent, Long> {

//    @Query("select u.nickZalo as NickZalo, u.inGame as InGame, ue.statusDongTien as StatusDongTien, ue.tienDong as DaDong, ue.tienHoTroGiai as HoTroGiai " +
//            "from User u join UserEvent ue on u.id = ue.user.id where ue.event.id = :userEventId ")
    @Query(value = "SELECT ROW_NUMBER() OVER(ORDER BY ue.id asc) as STT, ue.id as ID,  u.nick_zalo as NickZalo, u.in_game as InGame, ue.status_dong_tien as StatusDongTien, ue.tien_dong as DaDong, ue.tien_ho_tro_giai as HoTroGiai " +
            "FROM users as u JOIN user_event as ue on u.id = ue.user_id WHERE ue.event_id = :userEventId ORDER BY ue.status_dong_tien asc ", nativeQuery = true)
    List<UserEventView> findUserByEventId(Long userEventId);


    @Query("select count(ue.id)>0 from UserEvent ue  where ue.event.eventCode = :eventCode and ue.user.id = :userId")
    boolean existsBUserAndEvent(String eventCode, Long userId);
}
