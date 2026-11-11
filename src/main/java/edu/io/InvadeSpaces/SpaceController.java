package edu.io.InvadeSpaces;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/spaces")
public class SpaceController {
    private final SpaceService spaceService;

    public SpaceController(SpaceService spaceService){
        this.spaceService = spaceService;
    }
    
    @GetMapping("")
    public ResponseEntity<List<Space>> findAll() {
        return ResponseEntity.ok(spaceService.findAll());
    }

    @PostMapping("")
    public ResponseEntity<Space> newSpace(@RequestBody Space newSpace) {
        return ResponseEntity.ok(spaceService.save(newSpace));
    }

    @GetMapping(value = "", params = "name")
    public ResponseEntity<List<Space>> findByName(@RequestParam String name) {
        var spaceOption = spaceService.findByName(name);
        if (spaceOption.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(spaceOption.get());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Space> update(@PathVariable Long id, @RequestBody Space newSpace) {
        return ResponseEntity.ok(spaceService.update(id, newSpace));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSpace(@PathVariable Long id) {
        if (spaceService.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
