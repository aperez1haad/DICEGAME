package com.example.JuegoDeDados.Utils;

public class Constants {
    //ENDPOINTS
    public static final String playerControllerRequestMapping = "/players";
    public static final String createPlayer = "/create";
    public static final String updatePlayerName = "/update/{id}";
    public static final String deletePlayer = "/delete/{id}";
    public static final String getAllPlayers = "/getAll";
    public static final String getPlayerGames = "/{id}/games";
    public static final String calculateSuccessPercentage = "/{id}/successPercentage";
    public static final String calculateAverageSuccessPercentage = "/averageSuccess";
    public static final String getRankingBasedOnSuccessPercentage = "/ranking";
    public static final String getLoser = "/ranking/loser";
    public static final String getWinner = "/ranking/winner";
    public static final String gameControllerRequestMapping = "/players/{playerId}/games";
    public static final String playGame = "/play";
    public static final String deleteGamesForPlayer = "/delete";
    public static final String AuthenticationControllerRequestMapping = "/auth";
    public static final String register = "/register";
    public static final String authenticate = "/authenticate";

    //SWAGGER
    public static final String swaggerTagName = "Dice Game";
    public static final String swaggerTagDescription = "Operations pertaining to a Dice Game and its player Management";
    public static final String createPlayerSummary = "Player creation";
    public static final String createPlayerDescription = "This API allows you to create a new players.";
    public static final String createPlayerParam = "Player object";
    public static final String updatePlayerSummary = "Player update";
    public static final String updatePlayerDescr = "This API allows you to update existing players.";
    public static final String updatePlayerParam = "Player id and New Player name";
    public static final String deletePlayerSummary = "Player deletion";
    public static final String deletePlayerDescr = "This API allows you to delete players.";
    public static final String deletePlayerParam = "Player id";
    public static final String getAllPlayersSummary = "Player List";
    public static final String getAllPlayersDescr = "This API allows you to list all players.";
    public static final String getPlayerGamesSummary = "Player Game Record";
    public static final String getPlayerGamesDescr = "This API allows you to list the Game Record of a given Player.";
    public static final String getPlayerGamesParam = "Player object";
    public static final String calculateSuccessPercentageSummary = "Player Success Percentage";
    public static final String calculateSuccessPercentageDescr = "This API shows the Success Percentage of a given Player.";
    public static final String calculateSuccessPercentageParam = "Player object";
    public static final String calculateAvSccssPrcntgeSummary = "Average Success Percentage";
    public static final String calculateAvSccssPrcntgeDscr = "This API shows the Average Success Percentage of all the game history";
    public static final String getRankingSummary = "Player ranking";
    public static final String getRankingDescr = "This API shows a ranking of Players based on their success percentage";
    public static final String getLoserSummary = "Loser";
    public static final String getLoserDscr = "This API shows the Player with the lower Success Percentage";
    public static final String getWinnerSummary = "Winner";
    public static final String getWinnerDscr = "This API shows the Player with the higher Success Percentage";
    public static final String playGameSummary = "Play a game for a specific player";
    public static final String playGameDsc = "This API allows you to create a new games.";
    public static final String playGameParam = "The player id";
    public static final String deleteGamesForPlayerSummary = "Delete all game records for a specific player";
    public static final String deleteGamesForPlayerDscr = "This API allows you to delete game record for a given player";
    public static final String deleteGamesForPlayerParam = "The player id";
    public static final String registerSwaggerSummary = "Register a User with email and password";
    public static final String registerSwaggerDscr = "This API allows you to register a User and recieve a JWT Token";
    public static final String registerSwaggerParam = "Register Request";
    public static final String authenticateSwaggerSummary = "Authenticate a User with email and password";
    public static final String authenticateSwaggerDscr = "This API allows you to authenticate a User and recieve a JWT Token";
    public static final String authenticateSwaggerParam = "Authentication Request";

    //RESPONSE ENTITY BODY
    public static final String deletePlayerResponseBody = "Player has been deleted.";
    public static final String deleteGamesForPlayerResponseBody = "All games for the player have been deleted.";

    //GAME SERVICE
    public static final String PlayerNotFoundExceptionMessage = "Player not found with ID: ";

    //PLAYER SERVICE
    public static final String playerDefaultName = "ANONYMOUS";
    public static final String PlayerNameAlreadyUsedExceptionMessage = "Player name already exists in database: ";

    //APPLICATION CONFIG
    public static final String UsernameNotFoundException = "User not found";

    //JWT AUTHENTICATION FILTER
    public static final String authHeader = "Authorization";
    public static final String authHeaderStartsWith = "Bearer ";

    //JWT SERVICE
    public static final String secretKeyValue = "${secret.key}";

    //SECURITY CONFIG
    public static final String securityFilterChainRequestMatchers = "/auth/**";


}
