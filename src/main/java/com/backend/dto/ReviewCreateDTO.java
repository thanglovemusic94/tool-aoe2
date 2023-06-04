package com.backend.dto;

import com.backend.models.TYPE;



public class ReviewCreateDTO {

    private Long user_review_id;
    private Long user_id;
    private float point;
    private TYPE type;
    private String inGame;

    public String getInGame() {
        return inGame;
    }

    public void setInGame(String inGame) {
        this.inGame = inGame;
    }

    public Long getUser_review_id() {
        return user_review_id;
    }

    public void setUser_review_id(Long user_review_id) {
        this.user_review_id = user_review_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }
}
