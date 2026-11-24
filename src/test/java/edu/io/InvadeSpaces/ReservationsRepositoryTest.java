package edu.io.InvadeSpaces;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
public class ReservationsRepositoryTest {
    @Autowired 
    private ReservationReposityory reservationReposityory;

    @Autowired 
    private SpacesReposityory spacesReposityory;

    @Test
    void findById() {
        var space = new Space(1000L, "aaa", 5);
        spacesReposityory.save(space);
        var res = new Reservation(
            1000L,
            LocalDateTime.of(2026, 12, 12, 12, 12),
            LocalDateTime.of(2026, 12, 13, 12, 12),
            space
        );

        var saved = reservationReposityory.save(res);

        Assertions.assertTrue(res.equals(saved));
    }
}
