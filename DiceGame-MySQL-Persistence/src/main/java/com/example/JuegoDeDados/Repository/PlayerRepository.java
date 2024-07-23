package com.example.JuegoDeDados.Repository;

import com.example.JuegoDeDados.Model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Integer> {
    Optional<Player> findByEmail(String email);
}
