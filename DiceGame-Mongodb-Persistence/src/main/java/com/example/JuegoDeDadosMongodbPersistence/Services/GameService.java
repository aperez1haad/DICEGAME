package com.example.JuegoDeDadosMongodbPersistence.Services;


import com.example.JuegoDeDadosMongodbPersistence.Model.Game;
import com.example.JuegoDeDadosMongodbPersistence.Model.UserEntity;
import com.example.JuegoDeDadosMongodbPersistence.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GameService {


    @Autowired
    private UserRepository userRepository;

    public Game rollDice()
    {
        Game game = new Game();
        game.setDice1((int) (Math.random() * 6) + 1);
        game.setDice2((int) (Math.random() * 6) + 1);
        game.setWon(game.getDice1() + game.getDice2() == 7);
        return game;
    }
    public ResponseEntity<?> saveGame(@AuthenticationPrincipal UserDetails userDetails)
    {
        Game game = rollDice();
        String email = userDetails.getUsername();
        Optional<UserEntity> byEmail = userRepository.findByEmail(email);

        if(byEmail.isPresent()){

            UserEntity user = byEmail.get();
            user.getGames().add(game);
            userRepository.save(user);
            return ResponseEntity.ok(game);
        }

        return ResponseEntity.badRequest().body("User not found");
    }

    public ResponseEntity<?> deleteAllGames(@AuthenticationPrincipal UserDetails userDetails)
    {
        String email = userDetails.getUsername();
        Optional<UserEntity> byEmail = userRepository.findByEmail(email);
        if(byEmail.isPresent()) {

            UserEntity user = byEmail.get();
            user.setGames(new HashSet<>());
            userRepository.save(user);
            return ResponseEntity.ok("Games deleted");
        }

        return ResponseEntity.badRequest().body("User not found");
    }

    public void deleteGamesFromUser(UserEntity user)
    {
        user.setGames(new HashSet<>());
        userRepository.save(user);
    }

    public Set<Game> showGamesFromUser(@AuthenticationPrincipal UserDetails userDetails){

        String email = userDetails.getUsername();
        Optional<UserEntity> byEmail = userRepository.findByEmail(email);
        if(byEmail.isPresent()) {
            return byEmail.get().getGames();
        }
        return new HashSet<>();
    }

    /* Calculate the percentage of games won */
    public double calculatePercentageOfGamesWon(@AuthenticationPrincipal UserDetails userDetails)
    {
        String email = userDetails.getUsername();
        Optional<UserEntity> byEmail = userRepository.findByEmail(email);
        if(byEmail.isPresent()) {
            UserEntity user = byEmail.get();
            Set<Game> games = user.getGames();
            if(games.isEmpty())
            {
                return 0;
            }
            long gamesWon = games.stream().filter(Game::isWon).count();
            return (double) gamesWon / games.size() * 100;
        }
        return 0;
    }


    public double calculatePercentageOfGamesWon(UserEntity user)
    {
        Set<Game> games = user.getGames();
        if(games.isEmpty())
        {
            return 0;
        }
        long gamesWon = games.stream().filter(Game::isWon).count();
        return (double) gamesWon / games.size() * 100;
    }

    /* Calculate the average of all the players games */
    public double calculateAverageOfAllPlayersGames()
    {
        double average = userRepository.findAll().stream()
                .map(UserEntity::getGames)
                .flatMap(Set::stream)
                .mapToInt(game -> game.isWon() ? 1 : 0)
                .average()
                .orElse(0);

        return average * 100;
    }








}
