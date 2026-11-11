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
    @JsonProperty
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

    public String name() {
        return this.name;
    }

    public int capacity() {
        return this.capacity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
