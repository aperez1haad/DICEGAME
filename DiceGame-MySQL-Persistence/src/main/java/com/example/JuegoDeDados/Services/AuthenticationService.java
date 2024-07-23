package com.example.JuegoDeDados.Services;

import com.example.JuegoDeDados.Model.Player;
import com.example.JuegoDeDados.Model.Role;
import com.example.JuegoDeDados.Security.AuthenticationRequest;
import com.example.JuegoDeDados.Security.AuthenticationResponse;
import com.example.JuegoDeDados.Security.JwtService;
import com.example.JuegoDeDados.Security.RegisterRequest;

import com.example.JuegoDeDados.Repository.PlayerRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PlayerRepository playerRepository;
    private final PlayerService playerService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = Player.builder()
                .name(request.getName())
                .email(request.getEmail())
                .registrationDate(Calendar.getInstance().getTime())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        playerService.createPlayer(user);
        var jwtToken =jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );
        var user = playerRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken =jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
