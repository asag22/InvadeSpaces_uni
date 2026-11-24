package edu.io.InvadeSpaces;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository 
public interface ReservationReposityory extends JpaRepository<Reservation, Long> {

    // @Query("""
    //     SELECT r
    //     FROM reservations r
    //     JOIN FETCH 
    // """)
}