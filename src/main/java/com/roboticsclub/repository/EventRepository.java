package com.roboticsclub.repository;

import com.roboticsclub.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    // Used on the dashboard to show what's coming up next.
    List<Event> findByEventDateGreaterThanEqualOrderByEventDateAscEventTimeAsc(LocalDate fromDate);

    List<Event> findAllByOrderByEventDateAscEventTimeAsc();

    long countByEventDateGreaterThanEqual(LocalDate date);
}
