package com.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Entity
//@Table(name = "review",
//        uniqueConstraints = {
//                @UniqueConstraint(columnNames = {"user_id", "user_review_id", "type"})
//        })

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Setter
public class NhaTaiTro extends AbstractAuditingEntity<Long> implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String ten;
    private String soTien;
}
