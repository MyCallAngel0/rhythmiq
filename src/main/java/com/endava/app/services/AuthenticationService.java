package com.endava.app.services;

import com.endava.app.domain.User;
import com.endava.app.model.request.AuthenticationRequest;
import com.endava.app.model.request.RegisterRequest;
import com.endava.app.model.response.AuthenticationResponse;
import com.endava.app.repos.UserRepository;
import com.endava.app.util.exceptions.user.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    public AuthenticationResponse register(RegisterRequest request) {
        boolean isUserRegistered = userRepository.findByUsername(request.getUsername()).isPresent()
                || userRepository.findByEmail(request.getEmail()).isPresent();
        if (isUserRegistered) {
            throw new UserAlreadyExistsException("User with this email/username already exists!");
        }
        final User user = new User();
                user.setFirstname(request.getFirstname());
                user.setLastname(request.getLastname());
                user.setEmail(request.getEmail());
                user.setUsername(request.getUsername());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setDob(request.getDob());
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        storeTokenInFile(jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken).build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        final User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        storeTokenInFile(jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken).build();
    }

    private void storeTokenInFile(String token) {
        String filePath = "./src/main/resources/jwt.txt";
        try {
            byte[] contentBytes = token.getBytes();
            Path path = Paths.get(filePath);
            Files.write(path, contentBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUserCredentials() {
        String filePath = "./src/main/resources/jwt.txt";
        Path path = Paths.get(filePath);
        try {
            String token = Files.readString(path);
            if (!jwtService.isTokenExpired(token)) {
                log.info("Getting token for user {}", jwtService.extractUsername(token));
                return jwtService.extractUsername(token);
            }
            Files.write(path, new byte[0]);
            log.error("Token is expired!");
            return null;
        } catch (IOException e) {
            log.error("Failed to get token");
            return null;
        }
    }
}
