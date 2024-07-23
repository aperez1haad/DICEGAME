package com.example.JuegoDeDados.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int dice1;
    private int dice2;
    private boolean won;

    @ManyToOne
    @JoinColumn(name = "player_Id")
    @JsonBackReference//If i don't do this Circular reference in JSON serialization
    private Player player;

    public Game(int dice1, int dice2, Boolean won){
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.won = won;
    }
}
