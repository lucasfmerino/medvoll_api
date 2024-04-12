package med.lfm.api.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.lfm.api.domain.user.UserRepository;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired UserRepository repository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                System.out.println("chamando filtro");
        
        var tokenJWT = getToken(request);


        if (tokenJWT != null) {
            // System.out.println(tokenJWT);
            var subject = tokenService.getSubject(tokenJWT);
            var user = repository.findByLogin(subject);
            // System.out.println(subject);
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("logado na requisição");
        }

        filterChain.doFilter(request, response);

    }

    private String getToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");

        // if (authorizationHeader == null) {
        //     throw new RuntimeException("JWT token not sent in Authorization header");
        // }

        // return authorizationHeader.replace("Bearer ", "");

        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "").trim();
    }

    return null;
    }

}
