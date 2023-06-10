package com.backend.repository;

import com.backend.dto.TongTienView;
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


    String queryView = "\tSELECT \t\t\t\t\t\t\t\t\t \n" +
            "\t\tROW_NUMBER() OVER(ORDER BY view1.diemtrungbinh desc) as STT,\n" +
            "\t\t(   \n" +
            "\t\t\t\t\t\t\t\t\t\tCASE         \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN ROW_NUMBER() OVER(ORDER BY view1.diemtrungbinh desc) <=12 THEN 'A ' \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN 12 < ROW_NUMBER() OVER(ORDER BY view1.diemtrungbinh desc) && \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tROW_NUMBER() OVER(ORDER BY view1.diemtrungbinh desc) <=24 THEN 'B'\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN 24 < ROW_NUMBER() OVER(ORDER BY view1.diemtrungbinh desc) && \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tROW_NUMBER() OVER(ORDER BY view1.diemtrungbinh desc) <=36 THEN 'C'\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN 36 < ROW_NUMBER() OVER(ORDER BY view1.diemtrungbinh desc)  THEN 'D'\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tELSE 'CHƯA CÓ HẠNG'        \n" +
            "\t\t\t\t\t\t\t\t\t\tEND  \n" +
            "\t\t) as `Hang`,\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t    \n" +
            "\n" +
            "\t\tview1.diemtrungbinh as `DiemTrungBinh`, \n" +
            "\t\tview1.inGame as `InGame`, \n" +
            "\t\tview1.nickZalo as `NickZalo`, \n" +
            "\t\tview2.ID as ID,\n" +
            "\t\tview2.user_id  as UserID,\n" +
            "\t\tview2.StatusDongTien as StatusDongTien, \n" +
            "\t\tview2.DaDong as DaDong, \n" +
            "\t\tview2.HoTroGiai as HoTroGiai\n" +
            "\tFROM view1, view2 WHERE view1.user_review_id = view2.user_id and view2.event_id = 2 ORDER BY view1.diemtrungbinh desc; ";
    @Query(value = queryView, nativeQuery = true)
    List<UserEventView> findUserByEventId2(Long userEventId);

    @Query("select count(ue.id)>0 from UserEvent ue  where ue.event.eventCode = :eventCode and ue.user.id = :userId")
    boolean existsBUserAndEvent(String eventCode, Long userId);

    @Query(value = "SELECT \n" +
            "\t\tsum(DaDong) as TongDaDongDong,\n" +
            "\t\tsum(HoTroGiai) + (SELECT sum(ntt.so_tien) as TongDaDongDong FROM nha_tai_tro ntt) \n" +
            "\t\tas TongHoTroGiai,\n" +
            "\t\t(sum(DaDong) + sum(HoTroGiai) + (SELECT sum(ntt.so_tien) as TongDaDongDong FROM nha_tai_tro ntt)  ) as `tongtien` \n" +
            "\t\tFROM tongtien ;", nativeQuery = true)
    TongTienView findOneTongTien();
}
