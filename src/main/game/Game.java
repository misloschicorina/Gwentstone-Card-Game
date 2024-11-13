package main.game;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.GameInput;
import fileio.StartGameInput;
import main.cards.Card;
import main.cards.Hero;
import main.cards.Minion;
import main.cards.SpecialCard;
import java.util.ArrayList;
import fileio.*;

import main.game.responses.*;

public class Game {
    private final GameInput gameInput;
    private final Player player1;
    private final Player player2;
    private final ArrayNode output;
    private final ObjectMapper objectMapper;
    private GameBoard gameBoard;
    private int manaToReceive;
    private int turnsPlayed;
    private int turn;
    private int roundsPlayed;

    public Game(GameInput gameInput, Player player1, Player player2, ObjectMapper objectMapper, ArrayNode output) {
        this.gameInput = gameInput;
        this.player1 = player1;
        this.player2 = player2;
        this.objectMapper = objectMapper;
        this.output = output;
        this.manaToReceive = 1;
        this.turnsPlayed = 0;
        this.roundsPlayed = 1;
    }

    public void play() {
        // Setup players and game board
        StartGameInput startGame = gameInput.getStartGame();
        int seed = startGame.getShuffleSeed();
        player1.startGame(startGame.getPlayerOneDeckIdx(), seed, startGame.getPlayerOneHero());
        player2.startGame(startGame.getPlayerTwoDeckIdx(), seed, startGame.getPlayerTwoHero());
        gameBoard = new GameBoard();
        turn = startGame.getStartingPlayer();

        ArrayList<ActionsInput> actions = gameInput.getActions();

        for (ActionsInput action : actions) {
            int playerId = action.getPlayerIdx();
            switch (action.getCommand()) {
                case "getPlayerDeck":
                    getPlayerDeck(playerId);
                    break;
                case "getPlayerHero":
                    getPlayerHero(playerId);
                    break;
                case "getPlayerTurn":
                    getPlayerTurn();
                    break;
                case "getPlayerMana":
                    getPlayerMana(playerId);
                    break;
                case "getCardsInHand":
                    getCardsInHand(playerId);
                    break;
                case "getCardsOnTable":
                    getCardsOnTable();
                    break;
                case "placeCard":
                    placeCard(action);
                    break;
                case "getCardAtPosition":
                    getCardAtPos(action);
                    break;
                case "cardUsesAttack":
                    cardAttackUse(action);
                    break;
                case "cardUsesAbility":
                    cardAbilityUse(action);
                    break;
                case "endPlayerTurn":
                    endPlayerTurn();
                    break;
                default:
                    break;
            }
        }
    }

    private void getPlayerDeck(int playerId) {
        ArrayList<Card> deck = (playerId == 1) ? player1.getCurrentDeck().getCardsfromDeck() : player2.getCurrentDeck().getCardsfromDeck();
        GetPlayerDeckResponse response = new GetPlayerDeckResponse("getPlayerDeck", deck, playerId);
        addResponseToOutput(response);
    }

    private void getPlayerHero(int playerId) {
        Hero hero = (playerId == 1) ? player1.getHero() : player2.getHero();
        GetPlayerHeroResponse response = new GetPlayerHeroResponse("getPlayerHero", hero, playerId);
        addResponseToOutput(response);
    }

    private void getPlayerTurn() {
        GetPlayerTurnResponse response = new GetPlayerTurnResponse("getPlayerTurn", turn);
        addResponseToOutput(response);
    }

    private void getPlayerMana(int playerId) {
        int mana = (playerId == 1) ? player1.getMana() : player2.getMana();
        GetPlayerManaResponse response = new GetPlayerManaResponse("getPlayerMana", playerId, mana);
        addResponseToOutput(response);
    }

    private void getCardsInHand(int playerId) {
        ArrayList<Card> hand = (playerId == 1) ? player1.getHand() : player2.getHand();
        GetCardsInHandResponse response = new GetCardsInHandResponse("getCardsInHand", hand, playerId);
        addResponseToOutput(response);
    }

    private void getCardsOnTable() {
        ArrayList<ArrayList<Card>> allCards = gameBoard.getAllCardsOnTable();
        GetCardsOnTableResponse response = new GetCardsOnTableResponse("getCardsOnTable", allCards);
        addResponseToOutput(response);
    }

    private void placeCard(ActionsInput action) {
        int cardId = action.getHandIdx();
        Player player = (turn == 1) ? player1 : player2;
        Card cardToPlace = player.getCardFromHand(cardId);
        if (cardToPlace != null) {
            String error = "";

            if (!player.hasMana(cardToPlace)) {
                error = "Not enough mana to place card on table.";
            } else {
                int hasPlace = gameBoard.addCardOnBoard(gameBoard, cardToPlace, turn);
                if (hasPlace == 1) {
                    player.decreaseMana(cardToPlace.getMana());
                    player.getHand().remove(cardToPlace);
                } else if (hasPlace == 0) {
                    error = "Cannot place card on table since row is full.";
                }
            }

            if (!error.isEmpty()) {
                PlaceCardResponse response = new PlaceCardResponse("placeCard", cardId, error);
                addResponseToOutput(response);
            }
        }
    }

    private void getCardAtPos(ActionsInput action) {
        int x = action.getX();
        int y = action.getY();

        String error = "";
        Card card = gameBoard.getCardOnTable(gameBoard, x, y);  // Get the card at the position

        if (card == null) {
            error = "No card available at that position.";
            GetCardAtPositionInvalidResponse response =
                    new GetCardAtPositionInvalidResponse("getCardAtPosition", x, y, error);
            addResponseToOutput(response);
        } else {
            GetCardAtPositionValidResponse response =
                    new GetCardAtPositionValidResponse("getCardAtPosition", x, y, card);
            addResponseToOutput(response);
        }
    }


    private void cardAttackUse(ActionsInput action) {
        Coordinates coordsAtacked = action.getCardAttacked();
        int xAttacked = coordsAtacked.getX();
        int yAttacked = coordsAtacked.getY();

        Coordinates coordsAtacker = action.getCardAttacker();
        int xAttacker = coordsAtacker.getX();
        int yAttacker = coordsAtacker.getY();

        Card cardAttacked = gameBoard.getCardOnTable(gameBoard, xAttacked, yAttacked);
        Card cardAttacker = gameBoard.getCardOnTable(gameBoard, xAttacker, yAttacker);

        String error = "";
        
        if (cardAttacker == null) {
            error = "Attacker card does not exist on the board.";
        } else if (cardAttacked == null) {
            error = "Attacked card does not exist on the board.";
        } else {
            boolean isAttackerFrozen = false;
            int hasTank = gameBoard.hasTank(turn);

            if (cardAttacker instanceof Minion) {
                isAttackerFrozen = ((Minion) cardAttacker).isFrozen();
            } else if (cardAttacker instanceof SpecialCard) {
                isAttackerFrozen = ((SpecialCard) cardAttacker).isFrozen();
            }

            if (isAttackerFrozen) {
                error = "Attacker card is frozen.";
            } else if (cardAttacker.getHasUsedAttack() != 0) {
                error = "Attacker card has already attacked this turn.";
            } else if ((turn == 1 && xAttacked >= 2) || (turn == 2 && xAttacked < 2)) {
                error = "Attacked card does not belong to the enemy.";
            } else if (hasTank == 1) {
                if (cardAttacked instanceof Minion && !((Minion) cardAttacked).isTank()) {
                    error = "Attacked card is not of type 'Tank'.";
                }
            }
        }

        if (!error.isEmpty()) {
            CardUsesAttackResponse response =
                    new CardUsesAttackResponse("cardUsesAttack", coordsAtacked, coordsAtacker, error);
            addResponseToOutput(response);
        } else {
            // pot sa atac
            cardAttacker.setHasUsedAttack(1);
            int points = cardAttacker.getAttackDamage();
            cardAttacked.decreaseHealth(points);

            if (cardAttacked.getHealth() <= 0) {
                gameBoard.removeCardFromBoard(gameBoard, xAttacked, yAttacked);
            }
        }
    }

    private void cardAbilityUse(ActionsInput action) {

        Coordinates coordsAtacked = action.getCardAttacked();
        int xAttacked = coordsAtacked.getX();
        int yAttacked = coordsAtacked.getY();

        Coordinates coordsAtacker = action.getCardAttacker();
        int xAttacker = coordsAtacker.getX();
        int yAttacker = coordsAtacker.getY();

        Card cardAttacked = gameBoard.getCardOnTable(gameBoard, xAttacked, yAttacked);
        Card cardAttacker = gameBoard.getCardOnTable(gameBoard, xAttacker, yAttacker);

        String name = cardAttacker != null ? cardAttacker.getName() : "";

        String error = "";

        if (cardAttacker == null) {
            error = "Attacker card does not exist on the board.";
        } else if (cardAttacked == null) {
            error = "Attacked card does not exist on the board.";
        } else {
            if (cardAttacker instanceof SpecialCard && ((SpecialCard) cardAttacker).isFrozen()) {
                error = "Attacker card is frozen.";
            }
            else if (cardAttacker.getHasUsedAttack() != 0) {
                error = "Attacker card has already attacked this turn.";
            }
            else if (name.equals("Disciple")) {
                if ((turn == 1 && xAttacked < 2) || (turn == 2 && xAttacked >= 2)) {
                    error = "Attacked card does not belong to the current player.";
                }
            }
            else if (name.equals("The Ripper") || name.equals("Miraj") || name.equals("The Cursed One")) {
                if ((turn == 1 && xAttacked >= 2) || (turn == 2 && xAttacked < 2)) {
                    error = "Attacked card does not belong to the enemy.";
                } else {
                    int hasTank = gameBoard.hasTank(turn);
                    if (hasTank == 1 && ((cardAttacked instanceof Minion && !((Minion) cardAttacked).isTank()) ||
                            cardAttacked instanceof SpecialCard)) {
                        error = "Attacked card is not of type 'Tank'.";
                    }
                }
            }
        }

        if (!error.isEmpty()) {
            CardUsesAbilityResponse response =
                    new CardUsesAbilityResponse("cardUsesAbility", coordsAtacked, coordsAtacker, error);
            addResponseToOutput(response);
        } else {
            // marchez ca si a folosit atacul pe tura respectiva
            cardAttacker.setHasUsedAttack(1);

            if (name.equals("The Ripper")) {
                cardAttacked.setAttackDamage(Math.max(0, cardAttacked.getAttackDamage() - 2));
            } else if (name.equals("Miraj")) {
                int tempHealth = cardAttacker.getHealth();
                cardAttacker.setHealth(cardAttacked.getHealth());
                cardAttacked.setHealth(tempHealth);
            } else if (name.equals("The Cursed One")) {
                cardAttacked.swapHealthWithAttackDamage();
                if (cardAttacked.getHealth() <= 0) {
                    gameBoard.removeCardFromBoard(gameBoard, xAttacked, yAttacked);
                }
            } else if (name.equals("Disciple")) {
                cardAttacked.increaseHealth(2);
            }
        }
    }


    private void endPlayerTurn() {
        turnsPlayed++;

        if (turn == 1) {
            gameBoard.unfreezeAllForPlayerOne();
            gameBoard.resetCardsAttackForPlayer(gameBoard, player1);
        } else {
            gameBoard.unfreezeAllForPlayerTwo();
            gameBoard.resetCardsAttackForPlayer(gameBoard, player2);
        }

        turn = (turn == 1) ? 2 : 1;

        if (turnsPlayed % 2 == 0) {
            roundsPlayed++;
            player1.advanceToNextRound(roundsPlayed);
            player2.advanceToNextRound(roundsPlayed);
        }
    }

    private void addResponseToOutput(Object response) {
        output.add(objectMapper.convertValue(response, JsonNode.class));
    }
}
