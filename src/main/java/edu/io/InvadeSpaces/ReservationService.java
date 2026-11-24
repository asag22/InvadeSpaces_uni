package edu.io.InvadeSpaces;

import java.util.List;
import java.util.Optional;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationReposityory reservationReposityory;
    private final SpacesReposityory spacesReposityory;

    public ReservationService(ReservationReposityory reservationReposityory, SpacesReposityory spacesReposityory) {
        this.reservationReposityory = reservationReposityory;
        this.spacesReposityory = spacesReposityory;
    }

    public List<Reservation> getAll(){
        return reservationReposityory.findAll();
    }

    public Optional<Reservation> getById(long id) {
        return reservationReposityory.findById(id);
    }

    public Reservation Reserve(Reservation reservation, long spaceId) throws Exception {
        var space = spacesReposityory.findById(spaceId);
        if(space.isEmpty()) throw new Exception("space not found");
        reservation.setSpace(space.get());
        return reservationReposityory.save(reservation);
    }

    public Reservation update(Long id, Reservation reservation) {
        return reservationReposityory.findById(id).map(reser-> {
            reser.setStartTime(reservation.getStartTime());
            reser.setEndTime(reservation.getEndTime());
            return reservationReposityory.save(reser);
        }).orElseGet(() -> {
            return reservationReposityory.save(reservation);
        });
    }
}
