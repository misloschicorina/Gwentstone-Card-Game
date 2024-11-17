package main.game;

import main.cards.Card;
import main.cards.Minion;
import main.cards.SpecialCard;

import main.game.Player;

import java.util.ArrayList;

public final class GameBoard {

    public final int maxNumberOfCards = 5;
    public final int numberOfRows = 4;
    public final int numberOfColumns = 5;

    public static GameBoard instance = null;

    // matrice pt tabla de joc
    public Card[][] gameBoard;


    public GameBoard() {
        gameBoard = new Card[numberOfRows][numberOfColumns];
    }


//    public static GameBoard getInstance() {
//        if (instance == null) {
//            instance = new GameBoard();
//        }
//
//        return instance;
//    }
//
//    public static void setInstance(final GameBoard instance) {
//        GameBoard.instance = instance;
//    }


    public Card getCard(final int x, final int y) {
        // daca nu e carte acolo, returnez null
        if (x < 0 || x >= numberOfRows || y < 0 || y >= numberOfColumns) {
            return null;
        }
        return gameBoard[x][y];
    }


    public int placeCard(final int rowIndex, final Card card) {
        // verific daca randul e plin
        for (int col = 0; col < numberOfColumns; col++) {
            if (gameBoard[rowIndex][col] == null) {
                // pun cartea pe primul loc liber
                gameBoard[rowIndex][col] = card;
                return 1;
            }
        }
        return -1; // randul e plin
    }


    public Card[] getRow(final int rowIndex) {
        return gameBoard[rowIndex];
    }

    // dau freze la un row intreg
    public void freezeRow(final int affectedRow) {
        for (int i = 0; i < numberOfColumns; i++) {
            if (gameBoard[affectedRow][i] != null) {
                gameBoard[affectedRow][i].setFrozen(true);
            }
        }
    }


    // dezghet un row intreg
    public void unfreezeRow(final int affectedRow) {
        for (int i = 0; i < numberOfColumns; i++) {
            if (gameBoard[affectedRow][i] != null) {
                gameBoard[affectedRow][i].setFrozen(false);
            }
        }
    }

    public void unfreezeAllForPlayerOne() {
        unfreezeRow(2);  // Player 1 cards are on rows 2 and 3
        unfreezeRow(3);
    }

    public void unfreezeAllForPlayerTwo() {
        unfreezeRow(0);  // Player 2 cards are on rows 0 and 1
        unfreezeRow(1);
    }

    public ArrayList<ArrayList<Card>> getAllCardsOnTable() {
        ArrayList<ArrayList<Card>> allCards = new ArrayList<>();

        // Iterate through all rows and columns of the game board
        for (int row = 0; row < numberOfRows; row++) {
            ArrayList<Card> rowCards = new ArrayList<>();
            for (int col = 0; col < numberOfColumns; col++) {
                Card card = gameBoard[row][col];
                if (card != null) {
                    rowCards.add(card);
                }
            }
            allCards.add(rowCards);  // Add the row to the board's list of cards
        }

        return allCards;
    }

    public ArrayList<Card> getAllFrozenCardsOnTable() {
        ArrayList<Card> allFrozenCards = new ArrayList<>();

        // Iterate through all rows and columns of the game board
        for (int row = 0; row < numberOfRows; row++) {
            for (int col = 0; col < numberOfColumns; col++) {
                Card card = gameBoard[row][col];
                if (card != null) {
                    if (card.isFrozen() == true)
                    {
                        allFrozenCards.add(card);
                    }
                }
            }
        }
        return allFrozenCards;
    }

    public int addCardOnBoard(GameBoard board, Card card, int playerId) {
        int row_to_put = -1;

        // Determine the row based on playerId and card name
        if (playerId == 1) { // Player 1 can place cards on rows 2 and 3
            if (card.getName().equals("Sentinel") || card.getName().equals("Berserker"))
                row_to_put = 3;
            else if (card.getName().equals("Goliath") || card.getName().equals("Warden"))
                row_to_put = 2;
            else if (card.getName().equals("The Ripper") || card.getName().equals("Miraj"))
                row_to_put = 2;
            else if (card.getName().equals("The Cursed One") || card.getName().equals("Disciple"))
                row_to_put = 3;
        } else if (playerId == 2) { // Player 2 can place cards on rows 0 and 1
            if (card.getName().equals("Sentinel") || card.getName().equals("Berserker"))
                row_to_put = 0;
            else if (card.getName().equals("Goliath") || card.getName().equals("Warden"))
                row_to_put = 1;
            else if (card.getName().equals("The Ripper") || card.getName().equals("Miraj"))
                row_to_put = 1;
            else if (card.getName().equals("The Cursed One") || card.getName().equals("Disciple"))
                row_to_put = 0;
        }

        // Try to place the card in the chosen row
        for (int i = 0; i < 5; i++) {
            if (board.gameBoard[row_to_put][i] == null) {  // Find an empty spot
                board.gameBoard[row_to_put][i] = card;  // Place the card
                return 1;  // Return 1 indicating success
            }
        }

        // If no place was found, return 0
        return 0;
    }

    public Card getCardOnTable(GameBoard board, int x, int y) {
        return board.gameBoard[x][y];
    }

    public void resetCardsAttackForPlayer(GameBoard board, Player player) {
        int startRow;
        int endRow;

        if (player.getId() == 1) {
            startRow = 2;
            endRow = 3;
        } else { // playerId = 2
            startRow = 0;
            endRow = 1;
        }

        for (int i = startRow; i <= endRow; i++) {
            for (int j = 0; j < 5; j++) {
                Card card = board.gameBoard[i][j];
                if (card != null)
                    card.setHasUsedAttack(0);
            }
        }
    }

    // pt debug (sterge la final)
    public void printBoard() {
        for (int row = 0; row < numberOfRows; row++) {
            if (row < 2) {
                System.out.println("Player 1: ");
            } else {
                System.out.println("Player 2: ");
            }
            for (int col = 0; col < numberOfColumns; col++) {
                Card card = gameBoard[row][col];
                if (card instanceof Minion) {
                    Minion minion = (Minion) card;
                    if (minion.isTank()) {
                        System.out.println(minion.getName() + " is tank");
                    } else {
                        System.out.println(card.getName() + " normal");
                    }
                } else if (card instanceof SpecialCard) {
                    SpecialCard specialCard = (SpecialCard) card;
                    System.out.println(specialCard.getName() + " special");
                }
            }
        }
        System.out.println();
    }

    public int hasTank(int playerId) {
        int startRow;
        int endRow;

        if (playerId == 1) {
            startRow = 0;
            endRow = 1;
        } else { // playerId = 2
            startRow = 2;
            endRow = 3;
        }

        for (int i = startRow; i <= endRow; i++)
        {
            for (int j = 0; j < 5; j++) {
                Card card = gameBoard[i][j];
                if (card != null) {
                    if (card.isTank()) {
                        return 1; // am gasi tank uri pe tabla pe partea adversarului
                    }
                }
            }
        }
        return 0;
    }

    public int removeCardFromBoard(GameBoard board, int row, int col) {
        // Validăm coordonatele
        if (row < 0 || row >= numberOfRows || col < 0 || col >= numberOfColumns) {
            return -1; // Coordonate invalide
        }

        // Verificăm dacă există o carte pe poziția specificată
        if (board.gameBoard[row][col] == null) {
            return 0; // Nu există o carte de eliminat
        }

        // Eliminăm cartea și mutăm restul cărților spre stânga
        for (int i = col; i < numberOfColumns - 1; i++) {
            board.gameBoard[row][i] = board.gameBoard[row][i + 1];
        }

        // Setăm ultima coloană la `null`
        board.gameBoard[row][numberOfColumns - 1] = null;

        return 1; // Succes
    }

    public int whoseRowIsIt(int affectedRow) {
            if (affectedRow < 2)
                return 2;
            else
                return 1;
        }


    public void resetBoard() {
        // Iterate through each cell in the game board and set it to null
        for (int row = 0; row < numberOfRows; row++) {
            for (int col = 0; col < numberOfColumns; col++) {
                gameBoard[row][col] = null; // Clear the card from the cell
            }
        }
    }
}
