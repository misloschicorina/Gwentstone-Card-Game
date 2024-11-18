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
    private static final int MAX_COLUMNS = 5;
    private static final int MAX_MANA = 10;

    private final GameInput gameInput;
    private final Player player1;
    private final Player player2;
    private final ArrayNode output;
    private final ObjectMapper objectMapper;
    private GameBoard gameBoard;
    private int manaToReceive;
    private int turnsPlayed;
    private int turn;
    private int gamesPlayed;
    private int oneWins;
    private int twoWins;

    public Game(final GameInput gameInput, final Player player1, final Player player2,
                final ObjectMapper objectMapper, final ArrayNode output, final int gamesPlayed,
                final int oneWins, final int twoWins) {
        this.gameInput = gameInput;
        this.player1 = player1;
        this.player2 = player2;
        this.objectMapper = objectMapper;
        this.output = output;
        this.manaToReceive = 1;
        this.turnsPlayed = 0;
        this.gamesPlayed = gamesPlayed;
        this.oneWins = oneWins;
        this.twoWins = twoWins;
    }

    /**
     * Executes the main gameplay loop, processing actions and managing game state.
     */
    public void play() {
        // Configure the game
        StartGameInput startGame = gameInput.getStartGame();
        int seed = startGame.getShuffleSeed();

        player1.startGame(startGame.getPlayerOneDeckIdx(), seed, startGame.getPlayerOneHero());
        player2.startGame(startGame.getPlayerTwoDeckIdx(), seed, startGame.getPlayerTwoHero());

        gameBoard = new GameBoard();
        turn = startGame.getStartingPlayer();

        // Process actions
        ArrayList<ActionsInput> actions = gameInput.getActions();
        for (ActionsInput action : actions) {
            handleAction(action);
        }
    }

    private void handleAction(final ActionsInput action) {
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
            case "getFrozenCardsOnTable":
                getFrozenCardsOnTable();
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
            case "useAttackHero":
                attackHero(action);
                break;
            case "useHeroAbility":
                useHeroAbility(action);
                break;
            case "endPlayerTurn":
                endPlayerTurn();
                break;
            case "getTotalGamesPlayed":
                getTotalGamesPlayed();
                break;
            case "getPlayerOneWins":
                getPlayerOneWins();
                break;
            case "getPlayerTwoWins":
                getPlayerTwoWins();
                break;
            default:
                break;
        }
    }


    private void getPlayerDeck(final int playerId) {
        ArrayList<Card> deck;
        if (playerId == 1) {
            deck = player1.getCurrentDeck().getCardsfromDeck();
        } else {
            deck = player2.getCurrentDeck().getCardsfromDeck();
        }
        GetPlayerDeckResponse response =
                new GetPlayerDeckResponse("getPlayerDeck", deck, playerId);
        addResponseToOutput(response);
    }

    private void getPlayerHero(final int playerId) {
        Hero hero;
        if (playerId == 1) {
            hero = player1.getHero();
        } else {
            hero = player2.getHero();
        }
        GetPlayerHeroResponse response =
                new GetPlayerHeroResponse("getPlayerHero", hero, playerId);
        addResponseToOutput(response);
    }


    private void getPlayerTurn() {
        GetPlayerTurnResponse response = new GetPlayerTurnResponse("getPlayerTurn", turn);
        addResponseToOutput(response);
    }

    private void getPlayerMana(final int playerId) {
        int mana = (playerId == 1) ? player1.getMana() : player2.getMana();
        GetPlayerManaResponse response = new GetPlayerManaResponse("getPlayerMana", playerId, mana);
        addResponseToOutput(response);
    }

    private void getCardsInHand(final int playerId) {
        ArrayList<Card> hand = (playerId == 1) ? player1.getHand() : player2.getHand();
        GetCardsInHandResponse response =
                new GetCardsInHandResponse("getCardsInHand", hand, playerId);
        addResponseToOutput(response);
    }

    private void getCardsOnTable() {
        ArrayList<ArrayList<Card>> allCards = gameBoard.getAllCardsOnTable();
        GetCardsOnTableResponse response = new GetCardsOnTableResponse("getCardsOnTable", allCards);
        addResponseToOutput(response);
    }

    private void getFrozenCardsOnTable() {
        ArrayList<Card> allFrozenCards = gameBoard.getAllFrozenCardsOnTable();
        GetFrozenCardsResponse response =
                new GetFrozenCardsResponse("getFrozenCardsOnTable", allFrozenCards);
        addResponseToOutput(response);
    }


    private void placeCard(final ActionsInput action) {
        int cardId = action.getHandIdx();
        Player player = (turn == 1) ? player1 : player2;
        Card cardToPlace = player.getCardFromHand(cardId);
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

    private void getCardAtPos(final ActionsInput action) {
        int x = action.getX();
        int y = action.getY();

        String error = "";
        Card card = gameBoard.getCardOnTable(gameBoard, x, y);

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

    private void cardAttackUse(final ActionsInput action) {
        // Extract coordinates
        Coordinates coordsAtacked = action.getCardAttacked();
        int xAttacked = coordsAtacked.getX();
        int yAttacked = coordsAtacked.getY();

        Coordinates coordsAtacker = action.getCardAttacker();
        int xAttacker = coordsAtacker.getX();
        int yAttacker = coordsAtacker.getY();

        Card cardAttacked = gameBoard.getCardOnTable(gameBoard, xAttacked, yAttacked);
        Card cardAttacker = gameBoard.getCardOnTable(gameBoard, xAttacker, yAttacker);

        String error = "";

        // Validation checks
        if (cardAttacker == null) {
            return;
        } else if (cardAttacked == null) {
            return;
        } else {
            int hasTank = gameBoard.hasTank(turn);
            if (cardAttacker.isFrozen()) {
                error = "Attacker card is frozen.";
            } else if (cardAttacker.getHasUsedAttack() != 0) {
                error = "Attacker card has already attacked this turn.";
            } else if ((turn == 1 && xAttacked >= 2) || (turn == 2 && xAttacked < 2)) {
                error = "Attacked card does not belong to the enemy.";
            } else if (hasTank == 1 && !cardAttacked.isTank()) { // Polymorphic call
                    error = "Attacked card is not of type 'Tank'.";
            }

        }

        if (!error.isEmpty()) {
            CardUsesAttackResponse response =
                    new CardUsesAttackResponse("cardUsesAttack", coordsAtacked, coordsAtacker, error);
            addResponseToOutput(response);
            return;
        }

        // Execute attack
        cardAttacker.setHasUsedAttack(1);
        int points = cardAttacker.getAttackDamage();
        cardAttacked.decreaseHealth(points);

        // Remove card from the board if its health drops to zero
        if (cardAttacked.getHealth() <= 0) {
            gameBoard.removeCardFromBoard(gameBoard, xAttacked, yAttacked);
        }

    }

    private void cardAbilityUse(final ActionsInput action) {
        // Extract coordinates
        Coordinates coordsAtacked = action.getCardAttacked();
        int xAttacked = coordsAtacked.getX();
        int yAttacked = coordsAtacked.getY();

        Coordinates coordsAtacker = action.getCardAttacker();
        int xAttacker = coordsAtacker.getX();
        int yAttacker = coordsAtacker.getY();

        Card cardAttacked = gameBoard.getCardOnTable(gameBoard, xAttacked, yAttacked);
        Card cardAttacker = gameBoard.getCardOnTable(gameBoard, xAttacker, yAttacker);

        String name = cardAttacker.getName();

        String error = "";

        // Validation checks
        if (cardAttacker == null) {
            return;
        } else if (cardAttacked == null) {
            return;
        } else {
            if (cardAttacker.isFrozen()) {
                error = "Attacker card is frozen.";
            } else if (cardAttacker.getHasUsedAttack() != 0) {
                error = "Attacker card has already attacked this turn.";
            } else if (name.equals("Disciple")) {
                if ((turn == 1 && xAttacked < 2) || (turn == 2 && xAttacked >= 2)) {
                    error = "Attacked card does not belong to the current player.";
                }
            } else if (name.equals("The Ripper") || name.equals("Miraj") || name.equals("The Cursed One")) {
                if ((turn == 1 && xAttacked >= 2) || (turn == 2 && xAttacked < 2)) {
                    error = "Attacked card does not belong to the enemy.";
                } else {
                    int hasTank = gameBoard.hasTank(turn);
                    if (hasTank == 1 && ((cardAttacked instanceof Minion && !cardAttacked.isTank())
                            || cardAttacked instanceof SpecialCard)) {
                        error = "Attacked card is not of type 'Tank'.";
                    }
                }
            }
        }

        if (!error.isEmpty()) {
            CardUsesAbilityResponse response =
                    new CardUsesAbilityResponse("cardUsesAbility",
                            action.getCardAttacked(), action.getCardAttacker(), error);
            addResponseToOutput(response);
            return;
        }

        // Mark attacker as having used its ability this turn
        cardAttacker.setHasUsedAttack(1);

        // Execute the ability based on the attacker's name
        if (name.equals("The Ripper")) {
            cardAttacked.decreaseAttackDamage(2);
        } else if (name.equals("Miraj")) {
            int tempHealth = cardAttacker.getHealth();
            cardAttacker.setHealth(cardAttacked.getHealth());
            cardAttacked.setHealth(tempHealth);
        } else if (name.equals("The Cursed One")) {
            cardAttacked.swapHealthWithAttackDamage();
            if (cardAttacked.getHealth() <= 0) {
                // Remove card if health drops to zero
                gameBoard.removeCardFromBoard(gameBoard, xAttacked, yAttacked);
            }
        } else if (name.equals("Disciple")) {
            cardAttacked.increaseHealth(2);
        }
    }

    private void attackHero(final ActionsInput action) {
        // Extract coordinates
        Coordinates coordsAttacker = action.getCardAttacker();
        int xAttacker = coordsAttacker.getX();
        int yAttacker = coordsAttacker.getY();

        Card cardAttacker = gameBoard.getCardOnTable(gameBoard, xAttacker, yAttacker);

        String error = "";
        Player opponent = (turn == 1) ? player2 : player1;

        // Validation checks
        if (cardAttacker == null) {
            return;
        } else {
            if (cardAttacker.isFrozen()) {
                error = "Attacker card is frozen.";
            } else if (cardAttacker.getHasUsedAttack() != 0) {
                error = "Attacker card has already attacked this turn.";
            } else if (gameBoard.hasTank(turn) == 1) {
                error = "Attacked card is not of type 'Tank'.";
            }
        }

        if (!error.isEmpty()) {
            UseAttackHeroResponse response =
                    new UseAttackHeroResponse("useAttackHero", coordsAttacker, error);
            addResponseToOutput(response);
            return;
        }

        // Execute the attack on the hero
        cardAttacker.setHasUsedAttack(1); // Mark the attacking card as having used its attack
        int attackDamage = cardAttacker.getAttackDamage();
        opponent.getHero().decreaseHealth(attackDamage);

        // Check if the opponent's hero has been defeated
        if (opponent.getHero().getHealth() <= 0) {
            String gameEndedMessage;
            if (turn == 1) {
                gameEndedMessage = "Player one killed the enemy hero.";
                oneWins++;
            } else {
                gameEndedMessage = "Player two killed the enemy hero.";
                twoWins++;
            }

            GameEndedResponse gameEndedResponse = new GameEndedResponse(gameEndedMessage);
            addResponseToOutput(gameEndedResponse);
        }
    }

    private void useAbilityValid(final int affectedRow, final Hero hero) {
        String ability = hero.getAbility();

        if (ability.equals("Blood Thirst")) {
            for (int i = 0; i < MAX_COLUMNS; i++) {
                Card card = gameBoard.getGameBoard()[affectedRow][i];
                if (card != null) {
                    card.increaseAttackDamage(1);
                }
            }
        }

        if (ability.equals("Earth Born")) {
            for (int i = 0; i < MAX_COLUMNS; i++) {
                Card card = gameBoard.getGameBoard()[affectedRow][i];
                if (card != null) {
                    card.increaseHealth(1);
                }
            }
        }

        if (ability.equals("Low Blow")) {
            int indexCardToDestroy = -1;
            int maxHealth = -1;

            for (int i = 0; i < MAX_COLUMNS; i++) {
                Card card = gameBoard.getGameBoard()[affectedRow][i];
                if (card != null) {
                    if (card.getHealth() > maxHealth) {
                        maxHealth = card.getHealth();
                        indexCardToDestroy = i;
                    }
                }
            }

            if (indexCardToDestroy != -1) {
                gameBoard.removeCardFromBoard(gameBoard, affectedRow, indexCardToDestroy);
            }
        }

        if (ability.equals("Sub-Zero")) {
            for (int i = 0; i < MAX_COLUMNS; i++) {
                Card card = gameBoard.getGameBoard()[affectedRow][i];
                if (card != null) {
                    card.setFrozen(true);
                }
            }
        }
    }

    private void useHeroAbility(final ActionsInput action) {
        Player player; // Player whose hero uses ability
        if (turn == 1) {
            player = player1;
        } else {
            player = player2;
        }

        int affectedRow = action.getAffectedRow();
        Hero hero = player.getHero();
        String heroName = player.getHero().getName();
        int whoseRowIsIt = gameBoard.whoseRowIsIt(affectedRow);
        int heroMana = player.getHero().getMana();

        String error = "";

        // Validation checks for hero ability usage
        if (player.getMana() < heroMana) {
            error = "Not enough mana to use hero's ability.";
        } else if (player.getHero().getHasUsedAbility() == 1) {
            error = "Hero has already attacked this turn.";
        } else if ((heroName.equals("Lord Royce") || heroName.equals("Empress Thorina"))) {
            if (player.getId() == whoseRowIsIt) {
                error = "Selected row does not belong to the enemy.";
            }
        } else if ((heroName.equals("General Kocioraw") || heroName.equals("King Mudface"))) {
            if (player.getId() != whoseRowIsIt) {
                error = "Selected row does not belong to the current player.";
            }
        }

        if (!error.isEmpty()) {
            UseHeroAbilityResponse response =
                    new UseHeroAbilityResponse("useHeroAbility", affectedRow, error);
            addResponseToOutput(response);
            return;
        }

        // Execute the hero's ability
        useAbilityValid(affectedRow, hero);

        // Mark ability as used and deduct mana
        player.getHero().setHasUsedAbility(1);
        player.decreaseMana(heroMana);

    }

    private void endPlayerTurn() {
        turnsPlayed++;

        if (turn == 1) {
            gameBoard.unfreezeAllForPlayerOne();
            gameBoard.resetCardsAttackForPlayer(gameBoard, player1);
            player1.getHero().resetHeroAbility();
        } else {
            gameBoard.unfreezeAllForPlayerTwo();
            gameBoard.resetCardsAttackForPlayer(gameBoard, player2);
            player2.getHero().resetHeroAbility();
        }

        // Switch turn
        turn = (turn == 1) ? 2 : 1;

        // Increment mana only after both players' turns
        if (turnsPlayed % 2 == 0) {
            manaToReceive = Math.min(MAX_MANA, manaToReceive + 1);
            player1.advanceToNextRound(manaToReceive);
            player2.advanceToNextRound(manaToReceive);
        }
    }

    public void resetGameStats() {
        turnsPlayed = 0;
        manaToReceive = 1;
    }

    public void getTotalGamesPlayed() {
        GetStatsGameResponse response =
                new GetStatsGameResponse("getTotalGamesPlayed", gamesPlayed);
        addResponseToOutput(response);
    }

    public void getPlayerOneWins() {
        GetStatsGameResponse response =
                new GetStatsGameResponse("getPlayerOneWins", oneWins);
        addResponseToOutput(response);
    }

    public void getPlayerTwoWins() {
        GetStatsGameResponse response =
                new GetStatsGameResponse("getPlayerTwoWins", twoWins);
        addResponseToOutput(response);
    }

    public boolean hasPlayerOneWon() {
        return player2.getHero().getHealth() <= 0;
    }

    public boolean hasPlayerTwoWon() {
        return player1.getHero().getHealth() <= 0;
    }

    private void addResponseToOutput(final Object response) {
        output.add(objectMapper.convertValue(response, JsonNode.class));
    }

    public void resetGame() {
        gameBoard.resetBoard();
        player1.resetPlayer();
        player2.resetPlayer();
        this.manaToReceive = 1;
        this.turnsPlayed = 0;
    }
}
