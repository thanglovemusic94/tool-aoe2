package com.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "review",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "user_review_id", "type"})
        })
@DynamicUpdate
public class Review extends AbstractAuditingEntity<Long> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float point;

    private Long user_review_id;

    @Enumerated(EnumType.STRING)
    private TYPE type;

    @Enumerated(EnumType.STRING)
    private UserActive active = UserActive.ACTIVE;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private User user;


    public Review(float point, Long user_review_id, TYPE type, User user) {
        this.point = point;
        this.user_review_id = user_review_id;
        this.type = type;
        this.user = user;
    }

    public Review() {

    }

    public UserActive getActive() {
        return active;
    }

    public void setActive(UserActive active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public Long getUser_review_id() {
        return user_review_id;
    }

    public void setUser_review_id(Long user_review_id) {
        this.user_review_id = user_review_id;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
