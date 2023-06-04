package com.backend.models;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Embeddable
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserEvent extends AbstractAuditingEntity<Long> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id") // referencedColumnName id của user
    User user;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id") // referencedColumnName id của event
    Event event;

    @Column(name = "status_dong_tien")
    Boolean statusDongTien = false;
    Long tienDong;

    Long tienHoTroGiai;


    public Long getTienDong() {
        return tienDong;
    }

    public void setTienDong(Long tienDong) {
        this.tienDong = tienDong;
    }

    public Long getTienHoTroGiai() {
        return tienHoTroGiai;
    }

    public void setTienHoTroGiai(Long tienHoTroGiai) {
        this.tienHoTroGiai = tienHoTroGiai;
    }

    public Boolean getStatusDongTien() {
        return statusDongTien;
    }

    public void setStatusDongTien(Boolean statusDongTien) {
        this.statusDongTien = statusDongTien;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
