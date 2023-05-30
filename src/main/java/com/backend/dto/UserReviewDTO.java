package com.backend.dto;

import com.backend.models.TYPE;

import java.math.BigDecimal;
import java.math.BigInteger;

public class UserReviewDTO {


    private BigInteger rank;
    private String hang;
    private Double diemtrungbinh;
    public BigInteger user_review_id;

    public String inGame;
    public String nickZalo;
    public Long soNguoiChamDiem;


    public Long getSoNguoiChamDiem() {
        return soNguoiChamDiem;
    }

    public void setSoNguoiChamDiem(Long soNguoiChamDiem) {
        this.soNguoiChamDiem = soNguoiChamDiem;
    }

    public BigInteger getRank() {
        return rank;
    }

    public void setRank(BigInteger rank) {
        this.rank = rank;
    }

    public String getHang() {
        return hang;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public Double getDiemtrungbinh() {
        return diemtrungbinh;
    }

    public void setDiemtrungbinh(Double diemtrungbinh) {
        this.diemtrungbinh = diemtrungbinh;
    }

    public BigInteger getUser_review_id() {
        return user_review_id;
    }

    public void setUser_review_id(BigInteger user_review_id) {
        this.user_review_id = user_review_id;
    }

    public String getInGame() {
        return inGame;
    }

    public void setInGame(String inGame) {
        this.inGame = inGame;
    }

    public String getNickZalo() {
        return nickZalo;
    }

    public void setNickZalo(String nickZalo) {
        this.nickZalo = nickZalo;
    }

}
