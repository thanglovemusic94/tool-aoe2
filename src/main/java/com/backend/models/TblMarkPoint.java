//package com.backend.models;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
//@Entity
//@Table(name = "tblMarkPoint",
//        uniqueConstraints = {
//                @UniqueConstraint(columnNames = {"UserID_Review", "UserID_Mark"})
//        })
//public class TblMarkPoint extends AbstractAuditingEntity<Long> implements Serializable  {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long markPointID;
//
//    private Float Point_ChienThuat;
//    private Float Point_DiQuan;
//    private Float Point_DieuR;
//    private Float Point_EpDoi;
//    private Float Point_PhatTrien;
//    private Float Point_ThaoTacTay;
//    private Float Point_ThuNha;
//
//    private Long UserID_Review;
//
//    @Enumerated(EnumType.STRING)
//    private UserActive active = UserActive.ACTIVE;
//
//    @ManyToOne
//    @JoinColumn(name = "UserID_Mark")
//    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
//    private User user;
//
//    public Long getMarkPointID() {
//        return markPointID;
//    }
//
//    public void setMarkPointID(Long markPointID) {
//        this.markPointID = markPointID;
//    }
//
//    public float getPoint_ChienThuat() {
//        return Point_ChienThuat;
//    }
//
//    public void setPoint_ChienThuat(float point_ChienThuat) {
//        Point_ChienThuat = point_ChienThuat;
//    }
//
//    public float getPoint_DiQuan() {
//        return Point_DiQuan;
//    }
//
//    public void setPoint_DiQuan(float point_DiQuan) {
//        Point_DiQuan = point_DiQuan;
//    }
//
//    public float getPoint_DieuR() {
//        return Point_DieuR;
//    }
//
//    public void setPoint_DieuR(float point_DieuR) {
//        Point_DieuR = point_DieuR;
//    }
//
//    public float getPoint_EpDoi() {
//        return Point_EpDoi;
//    }
//
//    public void setPoint_EpDoi(float point_EpDoi) {
//        Point_EpDoi = point_EpDoi;
//    }
//
//    public float getPoint_PhatTrien() {
//        return Point_PhatTrien;
//    }
//
//    public void setPoint_PhatTrien(float point_PhatTrien) {
//        Point_PhatTrien = point_PhatTrien;
//    }
//
//    public float getPoint_ThaoTacTay() {
//        return Point_ThaoTacTay;
//    }
//
//    public void setPoint_ThaoTacTay(float point_ThaoTacTay) {
//        Point_ThaoTacTay = point_ThaoTacTay;
//    }
//
//    public float getPoint_ThuNha() {
//        return Point_ThuNha;
//    }
//
//    public void setPoint_ThuNha(float point_ThuNha) {
//        Point_ThuNha = point_ThuNha;
//    }
//
//    public Long getUserID_Review() {
//        return UserID_Review;
//    }
//
//    public void setUserID_Review(Long userID_Review) {
//        UserID_Review = userID_Review;
//    }
//
//    public UserActive getActive() {
//        return active;
//    }
//
//    public void setActive(UserActive active) {
//        this.active = active;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    @Override
//    public Long getId() {
//        return markPointID;
//    }
//
//
//}
