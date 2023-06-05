package com.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Persister;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Entity
@Table(name = "review",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "user_review_id", "type"})
        })
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Setter
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
    @JsonIgnoreProperties(value = {"user"}, allowSetters = true)
    private User user;

    @Column(columnDefinition = "int default 1")
    private Integer heSo;

    private Integer heSoSolo;
    private Integer heSo22;
    private Integer heSo44;

}
