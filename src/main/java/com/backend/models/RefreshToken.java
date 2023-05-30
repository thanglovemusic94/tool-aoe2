//package com.backend.models;
//
//import javax.persistence.*;
//import java.time.Instant;
//
//@Entity(name = "refreshtoken")
//public class RefreshToken {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @OneToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;
//
//    @Column(nullable = false, unique = true)
//    private String token;
//
//    @Column(nullable = false)
//    private Instant expiryDate;
//
//    //getters and setters
//
//}