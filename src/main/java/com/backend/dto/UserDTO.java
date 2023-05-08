package com.backend.dto;

import com.backend.models.TYPE;

import java.util.Objects;

public class UserDTO {
    private Long user_id;
    private Long user_review_id;
    private String inGame;
    private TYPE type;
    private int point;

    public UserDTO(Long user_id, String inGame) {
        this.user_id = user_id;
        this.inGame = inGame;
    }

    public UserDTO(Long user_id, Long user_review_id, TYPE type, int point) {
        this.user_id = user_id;
        this.user_review_id = user_review_id;
        this.type = type;
        this.point = point;
    }

    public UserDTO(Long user_id, Long user_review_id, String inGame, TYPE type, int point) {
        this.user_id = user_id;
        this.user_review_id = user_review_id;
        this.type = type;
        this.point = point;
        this.inGame = inGame;
    }

    public UserDTO() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO dto = (UserDTO) o;
        //return point == dto.point && Objects.equals(user_review_id, dto.user_id) && Objects.equals(user_review_id, dto.user_review_id) && Objects.equals(inGame, dto.inGame) && type == dto.type;
        return Objects.equals(inGame, dto.inGame);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, user_review_id, inGame, type, point);
    }

    public Long getUser_id() {
        return user_id;
    }

    public Long getUser_review_id() {
        return user_review_id;
    }

    public String getInGame() {
        return inGame;
    }

    public TYPE getType() {
        return type;
    }

    public int getPoint() {
        return point;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public void setUser_review_id(Long user_review_id) {
        this.user_review_id = user_review_id;
    }

    public void setInGame(String inGame) {
        this.inGame = inGame;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
