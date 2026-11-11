package edu.io.InvadeSpaces;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository 
public interface SpacesReposityory extends JpaRepository<Space, Long> {
    
    @Query("SELECT s FROM Space s WHERE s.name = ?1")
    Optional<List<Space>> findByName(String name);
}
