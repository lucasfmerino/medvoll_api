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
import med.lfm.api.domain.user.User;
import med.lfm.api.infra.security.TokenDTO;
import med.lfm.api.infra.security.TokenService;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;
    
    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid AuthDTO data) {
        try{

            var authToken = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
            var authentication = manager.authenticate(authToken);
    
            var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());
    
            return ResponseEntity.ok(new TokenDTO(tokenJWT));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
