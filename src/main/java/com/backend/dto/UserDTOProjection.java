package com.backend.dto;

import com.backend.models.TYPE;

public interface UserDTOProjection {
    public Long getUser_id();

    public Long getUser_review_id();

    public String getInGame();

    public TYPE getType();

    public int getPoint();


}
