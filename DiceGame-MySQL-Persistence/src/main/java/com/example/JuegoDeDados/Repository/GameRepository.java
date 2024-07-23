package com.example.JuegoDeDados.Repository;

import com.example.JuegoDeDados.Model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

}
