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

            // Initialize a Game instance with all required parameters
            Game game = new Game(gameInput, player1, player2, objectMapper, output);
            game.play();

            // Check if a player won and update statistics
            if (game.hasPlayerOneWon()) {
                playerOneWins++;
                playerTwoLost++;
            } else if (game.hasPlayerTwoWon()) {
                playerTwoWins++;
                playerOneLost++;
            }
        }
    }

    // Method to process session-wide commands after games have been played
    public void processSessionCommands(ArrayList<String> sessionCommands) {
        for (String command : sessionCommands) {
            handleSessionCommands(command);
        }
    }

    // Handle each session-wide command
    public void handleSessionCommands(String command) {
        switch (command) {
            case "getPlayerOneWins":
                addResponseToOutput(new GameStatsResponse("getPlayerOneWins", playerOneWins));
                break;
            case "getPlayerTwoWins":
                addResponseToOutput(new GameStatsResponse("getPlayerTwoWins", playerTwoWins));
                break;
            case "getTotalGamesPlayed":
                addResponseToOutput(new GameStatsResponse("getTotalGamesPlayed", playedGames));
                break;
            default:
                System.out.println("Unknown command: " + command);
                break;
        }
    }


    private void addResponseToOutput(Object response) {
        output.add(objectMapper.convertValue(response, JsonNode.class));
    }
}
