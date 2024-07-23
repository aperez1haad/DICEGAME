package com.example.JuegoDeDados.Config;

import com.example.JuegoDeDados.Model.Player;
import com.example.JuegoDeDados.Model.Role;
import com.example.JuegoDeDados.Services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verifica si el usuario admin ya existe
        List<Player> admins = playerService.getAllPlayers().stream()
                .filter(player -> Role.Admin.equals(player.getRole()))
                .collect(Collectors.toList());
        if (admins.isEmpty()) {
            // Crea el usuario admin si no existe
            Player admin = new Player();
            admin.setName("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("1234"));
            admin.setRole(Role.Admin);
            playerService.createPlayer(admin);
            System.out.println("Admin user created");
        } else {
            System.out.println("Admin user already exists");
        }
    }
}
