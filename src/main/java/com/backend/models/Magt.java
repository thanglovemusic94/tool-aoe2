package com.backend.models;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.io.Serializable;

@Entity
@Table(name = "Magt",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "code")
        })

@EntityListeners(AuditingEntityListener.class)
public class Magt extends AbstractAuditingEntity<Long> implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String note;
    @Enumerated(EnumType.STRING)
    private StatusMagt status = StatusMagt.HIEU_LUC;

    @OneToOne
    @JoinColumn(unique = false, nullable = true)
    private User user;
    public Magt(String code, String note) {
        this.code = code;
        this.note = note;
    }

    public Magt(String code) {
        this.code = code;
    }

    public Magt() {

    }

    public StatusMagt getStatus() {
        return status;
    }

    public void setStatus(StatusMagt status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
