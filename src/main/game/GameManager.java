package main.game;

import fileio.DecksInput;
import fileio.GameInput;
import fileio.Input;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;

/**
 * Manages multiple games between two players.
 * Handles initializing players, running games, and updating scores.
 */
public final class GameManager {
    private final ObjectMapper objectMapper;
    private final Input input;
    private final ArrayNode output;
    private int playedGames = 0;
    private int playerOneWins = 0;
    private int playerTwoWins = 0;

    public GameManager(final ObjectMapper objectMapper, final Input input, final ArrayNode output) {
        this.objectMapper = objectMapper;
        this.input = input;
        this.output = output;
    }

    /**
     * Manages all games by initializing players, running games, and updating global scores.
     */
    public void manageGames() {
        DecksInput playerOneDecksInput = input.getPlayerOneDecks();
        DecksInput playerTwoDecksInput = input.getPlayerTwoDecks();
        ArrayList<GameInput> games = input.getGames();

        for (GameInput gameInput : games) {
            playedGames++;

            Player player1 = new Player(1, playerOneWins, playerOneDecksInput.getNrDecks(),
                                                                            playerOneDecksInput);

            Player player2 = new Player(2, playerTwoWins, playerTwoDecksInput.getNrDecks(),
                                                                            playerTwoDecksInput);

            Game game = new Game(gameInput, player1, player2, objectMapper, output,
                                    playedGames, playerOneWins, playerTwoWins);

            game.play();

            // Update global scores based on game results
            if (game.hasPlayerOneWon()) {
                playerOneWins++;
            } else if (game.hasPlayerTwoWon()) {
                playerTwoWins++;
            }

            game.resetGame();
        }
    }
}
