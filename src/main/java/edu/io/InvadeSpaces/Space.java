package edu.io.InvadeSpaces;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "spaces")
@Getter
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@EqualsAndHashCode
public class Space {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @Column(nullable = false)
    @JsonProperty 
    private String name;

    @Column
    @JsonProperty
    private int capacity;

    public String to_String() {
        return String.format("Space {id: %i, nazwa; \"%s\", capacity: %d}", id, name, capacity);
    }
}