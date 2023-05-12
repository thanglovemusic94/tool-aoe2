package com.backend.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DiemTrungBinhViewDTO {

    private BigDecimal diemtrungbinh;
    public BigInteger user_review_id;
    public BigInteger soNguoiDanhGia;
    public String type;


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

    public BigInteger getSoNguoiDanhGia() {
        return soNguoiDanhGia;
    }

    public void setSoNguoiDanhGia(BigInteger soNguoiDanhGia) {
        this.soNguoiDanhGia = soNguoiDanhGia;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
