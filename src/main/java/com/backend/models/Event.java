package com.backend.models;

import com.backend.utils.RandomStringUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "event",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"eventCode"})
        })
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Event extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true, nullable = false)
    private String eventCode = RandomStringUtil.randomString(8);

    private String title;

    private String image;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String descriptionShort;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String descriptionLarge;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String term;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String note;

    private Instant start;
    private Instant end;

}
