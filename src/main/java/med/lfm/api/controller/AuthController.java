package med.lfm.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.lfm.api.domain.user.AuthDTO;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;
    
    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid AuthDTO data) {
        var token = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
        var authentication = manager.authenticate(token);

        return ResponseEntity.ok().build();
        // Ainda falta implementar o retorno da token.
    }
}
