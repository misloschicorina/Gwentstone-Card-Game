package main.game;
import fileio.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import main.cards.Card;
import main.cards.Hero;

import java.util.ArrayList;

public class GameManager {

    // The ObjectMapper is used to handle JSON data serialization and deserialization
    private final ObjectMapper objectMapper;

    // The Input class handles the game's input data, such as game setup, actions, etc.
    private final Input input;

    // ArrayNode is a type of JSON array, where the game results or actions will be outputted
    private final ArrayNode output;

    private final CommandExecutor commandExecutor;

    // Constructor to initialize the GameManager with the necessary objects
    public GameManager(final ObjectMapper objectMapper, final Input input, final ArrayNode output) {
        // Initialize the ObjectMapper that helps in reading and writing JSON data
        this.objectMapper = objectMapper;

        // Initialize the input object which contains the game's input data
        this.input = input;

        // Initialize the output array that will hold the results of the game or commands
        this.output = output;

        this.commandExecutor = new CommandExecutor(objectMapper, output); // Initialize Commands
    }

    // Method to play the game:
    public void play() {
        DecksInput playerOneDecksInput = input.getPlayerOneDecks();
        DecksInput playerTwoDecksInput = input.getPlayerTwoDecks();
        ArrayList<GameInput> games = input.getGames();

        Board board;

        int playedGames = 0;
        int playerOneWins = 0;
        int playerOneLost = 0;
        int playerTwoWins = 0;
        int playerTwoLost = 0;

        for (int i = 0; i < games.size(); i++) {

            playedGames++;
            StartGameInput startGame = games.get(i).getStartGame();
            int seed = startGame.getShuffleSeed();

            Player player1 = new Player(1, playerOneWins, playerOneLost, playerOneDecksInput.getNrDecks(), playerOneDecksInput);
            Player player2 = new Player(2, playerTwoWins, playerTwoLost, playerTwoDecksInput.getNrDecks(), playerTwoDecksInput);
            player1.startGame(startGame.getPlayerOneDeckIdx(), seed, startGame.getPlayerOneHero());
            player2.startGame(startGame.getPlayerTwoDeckIdx(), seed, startGame.getPlayerTwoHero());

            int manaIncrement = 1; // prima runda jucatorii primesc mana de adaugat = 1

            int turn = startGame.getStartingPlayer();
            int turnCount = 0;

            ArrayList<ActionsInput> actions = games.get(i).getActions();

            String command;

            for (int j = 0; j < actions.size(); j++) { // Fix condition to `j < actions.size()` instead of `i < actions.size()`
                command = actions.get(j).getCommand();
                int playerId = actions.get(j).getPlayerIdx();

                switch (command) {
                    case "getPlayerDeck":
                        ArrayList<Card> deck = null; // Initialize deck
                        if (playerId == 1)
                            deck = player1.getCurrentDeck().getCardsfromDeck();
                        if (playerId == 2)
                            deck = player2.getCurrentDeck().getCardsfromDeck();

                        commandExecutor.getPlayerDeck(playerId, deck);
                        break;

                    case "getPlayerHero":
                        Hero hero = null; // Initialize hero
                        if (playerId == 1)
                            hero = player1.getHero();
                        if (playerId == 2)
                            hero = player2.getHero();

                        commandExecutor.getPlayerHero(playerId, hero);
                        break;

                    case "getPlayerTurn":
                        commandExecutor.getPlayerTurn(turn); // Transmitere jucător activ către CommandExecutor
                        break;
                }
            }
        }
    }
}
