package com.example.JuegoDeDadosMongodbPersistence.Controllers;

import com.example.JuegoDeDadosMongodbPersistence.Controllers.Request.CreateUserDTO;
import com.example.JuegoDeDadosMongodbPersistence.Services.GameService;
import com.example.JuegoDeDadosMongodbPersistence.Services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameService gameService;


    @PostMapping()
    public ResponseEntity<?> createPlayer(@RequestBody CreateUserDTO createUserDTO)
    {
        return playerService.createPlayer(createUserDTO);

    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping()
    public ResponseEntity<?> updatePlayerName(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam String newName) {
        System.out.println(userDetails.getUsername());
        return playerService.updatePlayerName(userDetails, newName);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping()
    public ResponseEntity<?> deletePlayer(@AuthenticationPrincipal UserDetails userDetails)
    {
        return playerService.deletePlayer(userDetails.getUsername());
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping("/games")
    public ResponseEntity<?> deleteAllGames(@AuthenticationPrincipal UserDetails userDetails)
    {
        return ResponseEntity.ok(gameService.deleteAllGames(userDetails));
    }


    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/games")
    public ResponseEntity<?> getGames(@AuthenticationPrincipal UserDetails userDetails)
    {
        return ResponseEntity.ok(gameService.showGamesFromUser(userDetails));
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/score")
    public ResponseEntity<?> getScore(@AuthenticationPrincipal UserDetails userDetails)
    {
        return ResponseEntity.ok(gameService.calculatePercentageOfGamesWon(userDetails));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<?> getAllPlayers()
    {
        return ResponseEntity.ok(playerService.getPlayersRankedByPercentageOfGamesWon());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/ranking")
    public ResponseEntity<?> getRanking()
    {
        return ResponseEntity.ok(gameService.calculateAverageOfAllPlayersGames());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/ranking/looser")
    public ResponseEntity<?> getLooser()
    {
        return ResponseEntity.ok(playerService.getPlayerWithLowestPercentageOfGamesWon());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/ranking/winner")
    public ResponseEntity<?> getWinner()
    {
        return ResponseEntity.ok(playerService.getPlayerWithHighestPercentageOfGamesWon());
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/games")
    public ResponseEntity<?> createGame(@AuthenticationPrincipal UserDetails userDetails)
    {
        return ResponseEntity.ok(gameService.saveGame(userDetails));
    }





}
