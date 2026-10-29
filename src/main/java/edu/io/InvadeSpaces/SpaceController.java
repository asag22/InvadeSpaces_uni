package edu.io.InvadeSpaces;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class SpaceController {
    private final SpaceService spaceService;

    public SpaceController(SpaceService spaceService){
        this.spaceService = spaceService;
    }
    
    @GetMapping("/findall")
    public String getAll() {
        return spaceService.findAll().toString();
    }
}
