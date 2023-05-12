package com.backend.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DiemTrungBinhDTO {
    private BigInteger hang;
    private BigDecimal diemtrungbinh;
    public BigInteger user_review_id;

    public String inGame;
    public String nickZalo;
    public BigInteger soNguoiDanhGia;


    public BigInteger getHang() {
        return hang;
    }

    public void setHang(BigInteger hang) {
        this.hang = hang;
    }


    public BigDecimal getDiemtrungbinh() {
        return diemtrungbinh;
    }

    public void setDiemtrungbinh(BigDecimal diemtrungbinh) {
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

    public BigInteger getSoNguoiDanhGia() {
        return soNguoiDanhGia;
    }

    public void setSoNguoiDanhGia(BigInteger soNguoiDanhGia) {
        this.soNguoiDanhGia = soNguoiDanhGia;
    }
}
