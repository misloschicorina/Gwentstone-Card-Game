package main.game;

import main.cards.Card;

import java.util.ArrayList;

public final class GameBoard {
    private static final int MAX_ROWS = 4;
    private static final int MAX_COLUMNS = 5;
    private static final int PLAYER_ONE_START_ROW = 2;
    private static final int PLAYER_ONE_END_ROW = 3;
    private static final int PLAYER_TWO_START_ROW = 0;
    private static final int PLAYER_TWO_END_ROW = 1;

    private final Card[][] gameBoard; // The 2D array representing the board

    /**
     * Initializes an empty game board.
     */
    public GameBoard() {
        gameBoard = new Card[MAX_ROWS][MAX_COLUMNS];
    }

    /**
     * Retrieves the card at the specified position on the board.
     */
    public Card getCard(final int x, final int y) {
        if (x < 0 || x >= MAX_ROWS || y < 0 || y >= MAX_COLUMNS) {
            return null;
        }
        return gameBoard[x][y];
    }

    /**
     * Places a card in the specified row on the board.
     */
    public int placeCard(final int rowIndex, final Card card) {
        for (int col = 0; col < MAX_COLUMNS; col++) {
            if (gameBoard[rowIndex][col] == null) {
                gameBoard[rowIndex][col] = card;
                return 1;
            }
        }
        return -1; // Row is full
    }

    /**
     * Returns the entire game board as a 2D array.
     */
    public Card[][] getGameBoard() {
        return gameBoard;
    }

    /**
     * Retrieves all cards in a specific row.
     */
    public Card[] getRow(final int rowIndex) {
        return gameBoard[rowIndex];
    }

    /**
     * Freezes all cards in the specified row.
     */
    public void freezeRow(final int affectedRow) {
        for (int i = 0; i < MAX_COLUMNS; i++) {
            if (gameBoard[affectedRow][i] != null) {
                gameBoard[affectedRow][i].setFrozen(true);
            }
        }
    }

    /**
     * Unfreezes all cards in the specified row.
     */
    public void unfreezeRow(final int affectedRow) {
        for (int i = 0; i < MAX_COLUMNS; i++) {
            if (gameBoard[affectedRow][i] != null) {
                gameBoard[affectedRow][i].setFrozen(false);
            }
        }
    }

    /**
     * Unfreezes all cards belonging to Player 1.
     */
    public void unfreezeAllForPlayerOne() {
        unfreezeRow(PLAYER_ONE_START_ROW);
        unfreezeRow(PLAYER_ONE_END_ROW);
    }

    /**
     * Unfreezes all cards belonging to Player 2.
     */
    public void unfreezeAllForPlayerTwo() {
        unfreezeRow(PLAYER_TWO_START_ROW);
        unfreezeRow(PLAYER_TWO_END_ROW);
    }

    /**
     * Retrieves all cards currently on the table.
     */
    public ArrayList<ArrayList<Card>> getAllCardsOnTable() {
        ArrayList<ArrayList<Card>> allCards = new ArrayList<>();
        for (int row = 0; row < MAX_ROWS; row++) {
            ArrayList<Card> rowCards = new ArrayList<>();
            for (int col = 0; col < MAX_COLUMNS; col++) {
                Card card = gameBoard[row][col];
                if (card != null) {
                    rowCards.add(card);
                }
            }
            allCards.add(rowCards);
        }
        return allCards;
    }

    /**
     * Retrieves all frozen cards currently on the table.
     */
    public ArrayList<Card> getAllFrozenCardsOnTable() {
        ArrayList<Card> allFrozenCards = new ArrayList<>();
        for (int row = 0; row < MAX_ROWS; row++) {
            for (int col = 0; col < MAX_COLUMNS; col++) {
                Card card = gameBoard[row][col];
                if (card != null && card.isFrozen()) {
                    allFrozenCards.add(card);
                }
            }
        }
        return allFrozenCards;
    }

    /**
     * Adds a card to the board for a specific player.
     * @return 1 if the card was added successfully, 0 if the row is full
     */
    public int addCardOnBoard(final GameBoard board, final Card card, final int playerId) {
        int rowToPut = -1;
        String name = card.getName();

        // Determine the row based on playerId and card name
        if (playerId == 1) {
            if (name.equals("Sentinel") || name.equals("Berserker")) {
                rowToPut = PLAYER_ONE_END_ROW;
            } else if (name.equals("Goliath") || name.equals("Warden")) {
                rowToPut = PLAYER_ONE_START_ROW;
            } else if (name.equals("The Ripper") || name.equals("Miraj")) {
                rowToPut = PLAYER_ONE_START_ROW;
            } else if (name.equals("The Cursed One") || name.equals("Disciple")) {
                rowToPut = PLAYER_ONE_END_ROW;
            }
        } else if (playerId == 2) {
            if (name.equals("Sentinel") || name.equals("Berserker")) {
                rowToPut = PLAYER_TWO_START_ROW;
            } else if (name.equals("Goliath") || name.equals("Warden")) {
                rowToPut = PLAYER_TWO_END_ROW;
            } else if (name.equals("The Ripper") || name.equals("Miraj")) {
                rowToPut = PLAYER_TWO_END_ROW;
            } else if (name.equals("The Cursed One") || name.equals("Disciple")) {
                rowToPut = PLAYER_TWO_START_ROW;
            }
        }

        // Try to place the card in the chosen row
        for (int i = 0; i < MAX_COLUMNS; i++) {
            if (board.gameBoard[rowToPut][i] == null) { // Find an empty spot
                board.gameBoard[rowToPut][i] = card; // Place the card
                return 1; // Return 1 indicating success
            }
        }

        // If no place was found, return 0
        return 0;
    }

    /**
     * Retrieves the card at the specified position on the table.
     * @return the card at the given position
     */
    public Card getCardOnTable(final GameBoard board, final int x, final int y) {
        return board.gameBoard[x][y];
    }

    /**
     * Resets the attack state of all cards for a specific player.
     */
    public void resetCardsAttackForPlayer(final GameBoard board, final Player player) {
        int startRow = player.getId() == 1 ? PLAYER_ONE_START_ROW : PLAYER_TWO_START_ROW;
        int endRow = player.getId() == 1 ? PLAYER_ONE_END_ROW : PLAYER_TWO_END_ROW;

        for (int i = startRow; i <= endRow; i++) {
            for (int j = 0; j < MAX_COLUMNS; j++) {
                Card card = board.gameBoard[i][j];
                if (card != null) {
                    card.setHasUsedAttack(0);
                }
            }
        }
    }

    /**
     * Checks if there is a tank card in the specified player's area.
     * @return 1 if there is a tank card, 0 otherwise
     */
    public int hasTank(final int playerId) {
        int startRow = playerId == 1 ? PLAYER_TWO_START_ROW : PLAYER_ONE_START_ROW;
        int endRow = playerId == 1 ? PLAYER_TWO_END_ROW : PLAYER_ONE_END_ROW;

        for (int i = startRow; i <= endRow; i++) {
            for (int j = 0; j < MAX_COLUMNS; j++) {
                Card card = gameBoard[i][j];
                if (card != null && card.isTank()) {
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * Removes a card from the specified position on the board.
     * @return 1 if removed, 0 if no card exists, -1 if invalid position
     */
    public int removeCardFromBoard(final GameBoard board, final int row, final int col) {
        if (row < 0 || row >= MAX_ROWS || col < 0 || col >= MAX_COLUMNS) {
            return -1;
        }

        if (board.gameBoard[row][col] == null) {
            return 0;
        }

        // Shift cards in the row to the left, filling the gap created by removal
        for (int i = col; i < MAX_COLUMNS - 1; i++) {
            board.gameBoard[row][i] = board.gameBoard[row][i + 1];
        }

        // Set the last position in the row to null
        board.gameBoard[row][MAX_COLUMNS - 1] = null;
        return 1;
    }

    /**
     * Determines which player owns the specified row.
     * @return 1 for Player 1, 2 for Player 2
     */
    public int whoseRowIsIt(final int affectedRow) {
        return affectedRow < 2 ? 2 : 1;
    }

    /**
     * Resets the board by clearing all cards.
     */
    public void resetBoard() {
        for (int row = 0; row < MAX_ROWS; row++) {
            for (int col = 0; col < MAX_COLUMNS; col++) {
                gameBoard[row][col] = null;
            }
        }
    }
}

