package med.lfm.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/test")
@SecurityRequirement(name = "bearer-key") 
public class ControllerTest {
    
    @GetMapping
    public String controllerTest() {
        return "First Commit";
    }
}
