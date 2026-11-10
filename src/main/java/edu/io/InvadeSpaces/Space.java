package edu.io.InvadeSpaces;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "spaces")
public class Space {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @JsonProperty 
    private String name;

    @Column
    @JsonProperty
    private int capacity;

    public String to_String() {
        return String.format("Space {id: %i, nazwa; \"%s\", capacity: %d}", id, name, capacity);
    }
}
