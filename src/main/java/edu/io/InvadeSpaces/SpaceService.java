package edu.io.InvadeSpaces;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SpaceService {
    private final SpacesReposityory spacesReposityory;

    public SpaceService(SpacesReposityory spacesReposityory){
        this.spacesReposityory = spacesReposityory;
    }

    public List<Space> findAll() {
        return spacesReposityory.findAll();
    }

    public add(Space space) {
        spacesReposityory.save(space);
    }
}
