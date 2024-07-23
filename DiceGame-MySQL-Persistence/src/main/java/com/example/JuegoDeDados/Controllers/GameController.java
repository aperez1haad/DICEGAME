package com.example.JuegoDeDados.Controllers;


import com.example.JuegoDeDados.Services.GameService;
import com.example.JuegoDeDados.Model.Game;
import com.example.JuegoDeDados.Utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.gameControllerRequestMapping)
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping(Constants.playGame)
    @Operation(summary = Constants.playGameSummary, description = Constants.playGameDsc)
    @PreAuthorize("hasRole('ROLE_USER') and #playerId == authentication.principal.id")
    public ResponseEntity<Game> playGame(@Parameter(description = Constants.playGameParam, required = true) @PathVariable int playerId) {
        Game game = gameService.playGame(playerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }

    @DeleteMapping(Constants.deleteGamesForPlayer)
    @Operation(summary = Constants.deleteGamesForPlayerSummary, description = Constants.deleteGamesForPlayerDscr)
    @PreAuthorize("hasRole('ROLE_Admin') or (hasRole('ROLE_USER') and #playerId == authentication.principal.id)")
    public ResponseEntity<String> deleteGamesForPlayer(
            @Parameter(description = Constants.deleteGamesForPlayerParam, required = true) @PathVariable int playerId
    ) {
        gameService.deleteGamesForPlayer(playerId);

        return ResponseEntity.ok(Constants.deleteGamesForPlayerResponseBody);
    }
}