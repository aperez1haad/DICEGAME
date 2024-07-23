package com.example.JuegoDeDadosMongodbPersistence.Services;

import com.example.JuegoDeDadosMongodbPersistence.Controllers.Request.CreateUserDTO;
import com.example.JuegoDeDadosMongodbPersistence.Excepcions.PlayerNameAlreadyUsedException;
import com.example.JuegoDeDadosMongodbPersistence.Excepcions.PlayerNotFoundException;
import com.example.JuegoDeDadosMongodbPersistence.Model.RoleEntity;
import com.example.JuegoDeDadosMongodbPersistence.Model.UserEntity;
import com.example.JuegoDeDadosMongodbPersistence.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PlayerService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GameService gameService;

    //Create a player
    public ResponseEntity<?> createPlayer(CreateUserDTO createUserDTO)
    {

        String nameOk = checkPlayerName(createUserDTO.getUsername());

        UserEntity userEntity = UserEntity.builder()
                .email(createUserDTO.getEmail())
                .name(nameOk)
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .roles(Set.of(RoleEntity.builder().role(RoleEntity.ERole.USER).build()))
                .build();

        userRepository.save(userEntity);
        return ResponseEntity.ok(userEntity);
    }

    /* Check player name */
    public String checkPlayerName(String name)
    {
        Optional<UserEntity> byName = userRepository.findByName(name);
        String nameOk;

        if(name.isBlank()){
            nameOk = "Anonymous";
            return nameOk;

        }
        if(byName.isPresent()) {
            throw new PlayerNameAlreadyUsedException("Player name already used");
        } else {
            nameOk = name;
            return nameOk;
        }
    }

    /* Update player name */
    public ResponseEntity<?> updatePlayerName(String name, String newName)
    {
        Optional<UserEntity> byName = userRepository.findByName(name);

        if(byName.isPresent()) {
            String nameOk = checkPlayerName(newName);
            UserEntity userEntity = byName.get();

            userEntity.setName(nameOk);
            userRepository.save(userEntity);
            return ResponseEntity.ok(userEntity);
        } else {
            throw new PlayerNotFoundException("Player didn't exist");
        }
    }

      public ResponseEntity<?> updatePlayerName(@AuthenticationPrincipal UserDetails userDetails,  String newName)
    {
        Optional<UserEntity> byEmail = userRepository.findByEmail(userDetails.getUsername());

        if(byEmail.isPresent()) {
            String nameOk = checkPlayerName(newName);
            UserEntity userEntity = byEmail.get();

            userEntity.setName(nameOk);
            userRepository.save(userEntity);
            return ResponseEntity.ok(userEntity);
        } else {
            throw new PlayerNotFoundException("Player didn't exist");
        }
    }



    /* Get all players */
    public List<UserEntity> getAllPlayers()
    {
        return userRepository.findAll();
    }

    public ResponseEntity<?> deletePlayer(String email)

    {
        Optional<UserEntity> byName = userRepository.findByEmail(email);
        if(byName.isPresent()) {
            UserEntity user = byName.get();
            gameService.deleteGamesFromUser(user);
            userRepository.delete(user);
            return ResponseEntity.ok(user);
        } else {
            throw new PlayerNotFoundException("Player didn't exist");
        }
    }

    /* Return list of players ranked by the percentage of games won */


     public Set<UserEntity> getPlayersRankedByPercentageOfGamesWon()
    {
        List<UserEntity> allPlayers = getAllPlayers();

        allPlayers.sort((player1, player2) -> {
            double percentage1 = gameService.calculatePercentageOfGamesWon(player1);
            double percentage2 = gameService.calculatePercentageOfGamesWon(player2);
            return Double.compare(percentage2, percentage1);
        });

        return new HashSet<>(allPlayers);
    }

     /* Return the player with the highest percentage of games won */
    public UserEntity getPlayerWithHighestPercentageOfGamesWon()
    {
        return getPlayersRankedByPercentageOfGamesWon().stream().findFirst().orElse(null);
    }

      /* Return the player with the lowest percentage of games won */
    public UserEntity getPlayerWithLowestPercentageOfGamesWon()
    {
        List<UserEntity> allPlayers = getAllPlayers();

        allPlayers.sort((player1, player2) -> {
            double percentage1 = gameService.calculatePercentageOfGamesWon(player1);
            double percentage2 = gameService.calculatePercentageOfGamesWon(player2);
            return Double.compare(percentage1, percentage2);
        });

        return allPlayers.stream().findFirst().orElse(null);
    }



}
