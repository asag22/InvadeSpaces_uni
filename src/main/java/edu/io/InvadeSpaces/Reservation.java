package edu.io.InvadeSpaces;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @Column
    @JsonProperty
    private LocalDateTime startTime;

    @Column
    @JsonProperty
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "space_id", nullable = false)
    @JsonProperty
    private Space space;

    public void setSpace(Space space) {
        this.space = space;
    }
}
