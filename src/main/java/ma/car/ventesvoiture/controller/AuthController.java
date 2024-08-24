package ma.car.ventesvoiture.controller;

import ma.car.ventesvoiture.dto.LoginRequest;
import ma.car.ventesvoiture.entity.Users;
import ma.car.ventesvoiture.security.SecurityConfig;
import ma.car.ventesvoiture.service.CustomUserDetailsService;
import ma.car.ventesvoiture.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private JwtEncoder jwtEncoder;
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    private final AuthenticationManager authenticationManager;
   // private final JwtTokenProvider tokenProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    public AuthController(AuthenticationManager authenticationManager,JwtEncoder jwtEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Users savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }
    @PostMapping("/login")
    public Map<String ,String> authenticateUser(@RequestBody LoginRequest loginRequest) {
        System.out.println("AuthController: authenticateUser called with email: " + loginRequest.getEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        Instant instant=Instant.now();
        Users user = userService.findByEmail(loginRequest.getEmail()); // Vous devrez implémenter cette méthode dans votre UserService
        String userId = String.valueOf(user.getId());
        String scope=authentication.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(" "));
                JwtClaimsSet jwtClaimsSet=JwtClaimsSet.builder()
                        .issuedAt(instant)
                        .expiresAt(instant.plus (16, ChronoUnit.MINUTES))
                        .subject (loginRequest.getEmail())
                        .claim("scope", scope)
                        .claim("userId", userId)
                        .build();
        JwtEncoderParameters jwtEncoderParameters=
                JwtEncoderParameters.from(
                        JwsHeader.with(MacAlgorithm.HS512).build(), jwtClaimsSet
                );
        String jwt = jwtEncoder.encode (jwtEncoderParameters).getTokenValue();
        return Map.of("access-token",jwt);

       /* SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("AuthController: User authenticated: " + loginRequest.getEmail());
        return ResponseEntity.ok("Login successful");*/
    }
}
