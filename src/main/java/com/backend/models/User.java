package com.backend.models;


import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "inGame")
        })
public class User extends AbstractAuditingEntity<Long> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hovaten;
    private String inGame;
    private String nickZalo;
    @Size(max = 11, min = 9)
    private String sdt;
    private String nickFb;
    @Size(max = 120)
    private String password;

    @Enumerated(EnumType.STRING)
    private ERole roles;

    @Enumerated(EnumType.STRING)
    private UserActive active = UserActive.ACTIVE;
    public User() {
    }

    public User(String inGame, String password) {
        this.inGame = inGame;
        this.password = password;
    }


    public User(String hovaten, String inGame, String nickZalo, String sdt, String nickFb, String password) {
        this.hovaten = hovaten;
        this.inGame = inGame;
        this.nickZalo = nickZalo;
        this.sdt = sdt;
        this.nickFb = nickFb;
        this.password = password;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHovaten() {
        return hovaten;
    }

    public void setHovaten(String hovaten) {
        this.hovaten = hovaten;
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

    public String getSdt() throws Exception {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getNickFb() {
        return nickFb;
    }

    public void setNickFb(String nickFb) {
        this.nickFb = nickFb;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ERole getRoles() {
        return roles;
    }

    public void setRoles(ERole roles) {
        this.roles = roles;
    }

    public UserActive getActive() {
        return active;
    }

    public void setActive(UserActive active) {
        this.active = active;
    }
    private static String maskString(String strText, int start, int end, char maskChar)
            throws Exception{

        if(strText == null || strText.equals(""))
            return "";

        if(start < 0)
            start = 0;

        if( end > strText.length() )
            end = strText.length();

        if(start > end)
            throw new Exception("End index cannot be greater than start index");

        int maskLength = end - start;

        if(maskLength == 0)
            return strText;

        StringBuilder sbMaskString = new StringBuilder(maskLength);

        for(int i = 0; i < maskLength; i++){
            sbMaskString.append(maskChar);
        }

        return strText.substring(0, start)
                + sbMaskString.toString()
                + strText.substring(start + maskLength);
    }
}
