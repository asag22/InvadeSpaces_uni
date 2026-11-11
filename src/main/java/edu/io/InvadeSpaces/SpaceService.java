package edu.io.InvadeSpaces;

import java.util.List;
import java.util.Optional;

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

    public Space add(Space space) {
        return spacesReposityory.save(space);
    }

    public Optional<List<Space>> findByName(String name) {
        return spacesReposityory.findByName(name);
    }

    public Space save(Space space) {
        return spacesReposityory.save(space);
    }

    public Space update(long id, Space newSpace) {
        return spacesReposityory.findById(id).map(space -> {
            space.setName(newSpace.name());
            space.setCapacity(newSpace.capacity());
            return spacesReposityory.save(space);
        }).orElseGet(() -> {
            return spacesReposityory.save(newSpace);
        });
    }

    public boolean delete(Long id) {
        var space = spacesReposityory.findById(id);
        if (space.isPresent()) {
            spacesReposityory.delete(space.get());
            return true;    
        } else {
            return false;
        }
    }
}
