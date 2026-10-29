package edu.io.InvadeSpaces;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository 
public interface SpacesReposityory extends JpaRepository<Space, Long> {

}
