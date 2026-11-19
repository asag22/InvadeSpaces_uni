package edu.io.InvadeSpaces;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/reservations")
public class ReservationController {
    final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    
    @GetMapping("")
    public ResponseEntity<List<Reservation>> getAll(){
        return ResponseEntity.ok(reservationService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getById(@PathVariable long id) {
        var reservation = reservationService.getById(id);
        if(reservation.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservation.get());
    }

    @PostMapping("/reserve/{id}")
    public ResponseEntity<Reservation> reserveSpace(@PathVariable Long id, @RequestBody Reservation reservation) {
        try {
            return ResponseEntity.ok(reservationService.Reserve(reservation, id));
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
