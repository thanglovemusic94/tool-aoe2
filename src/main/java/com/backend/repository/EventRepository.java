package com.backend.repository;

import com.backend.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = "SELECT * FROM `event` ORDER BY id desc  LIMIT 0, 1 ", nativeQuery = true)
    Event findOneEventNew();

    Event findByEventCode(String code);



}
