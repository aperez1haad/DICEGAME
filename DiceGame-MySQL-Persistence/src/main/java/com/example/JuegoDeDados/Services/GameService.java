package com.example.JuegoDeDados.Services;

import com.example.JuegoDeDados.Model.Game;
import com.example.JuegoDeDados.Model.Player;
import com.example.JuegoDeDados.Exceptions.PlayerNotFoundException;
import com.example.JuegoDeDados.Repository.PlayerRepository;
import com.example.JuegoDeDados.Repository.GameRepository;
import com.example.JuegoDeDados.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerRepository playerRepository;

    public Game playGame(Integer playerId) {
        Game game = rollDice();

        Player player = findPlayer(playerId);
        player.addGame(game);
        playerRepository.save(player);

        return game;
    }

    private Game rollDice(){
        int dice1 = (int) (Math.random() * 6) + 1;
        int dice2 = (int) (Math.random() * 6) + 1;
        boolean won = (dice1 + dice2 == 7);

        return new Game(dice1,dice2,won);
    }

    public void deleteGamesForPlayer(Integer playerId) {
        Player player = findPlayer(playerId);
        player.deleteAllGames();
        playerRepository.save(player);
    }

    public List<Game> getGamesForPlayer(Integer playerId) {
        return findPlayer(playerId).getGames();
    }

    public Player findPlayer(Integer playerId){
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(Constants.PlayerNotFoundExceptionMessage + playerId));
    }
}