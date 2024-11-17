package main.game;

import fileio.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.ArrayList;
import main.game.responses.*;

public class GameManager {
    private final ObjectMapper objectMapper;
    private final Input input;
    private final ArrayNode output;
    private int playedGames = 0;
    private int playerOneWins = 0;
    private int playerOneLost = 0;
    private int playerTwoWins = 0;
    private int playerTwoLost = 0;

    public GameManager(final ObjectMapper objectMapper, final Input input, final ArrayNode output) {
        this.objectMapper = objectMapper;
        this.input = input;
        this.output = output;
    }

    public void manageGames() {
        DecksInput playerOneDecksInput = input.getPlayerOneDecks();
        DecksInput playerTwoDecksInput = input.getPlayerTwoDecks();
        ArrayList<GameInput> games = input.getGames();

        for (GameInput gameInput : games) {
            playedGames++;

            Player player1 = new Player(1, playerOneWins, playerOneLost, playerOneDecksInput.getNrDecks(), playerOneDecksInput);
            Player player2 = new Player(2, playerTwoWins, playerTwoLost, playerTwoDecksInput.getNrDecks(), playerTwoDecksInput);

            Game game = new Game(gameInput, player1 ,player2,
                    objectMapper,
                    output,
                    playedGames,
                    playerOneWins,
                    playerTwoWins);

            game.play();

            // Actualizez scorurile globale pe baza rezultatelor jocului
            if (game.hasPlayerOneWon())
                playerOneWins++;
            else if (game.hasPlayerTwoWon())
                playerTwoWins++;

            game.resetGame();
        }
    }
}