package main.game;

import fileio.DecksInput;
import fileio.CardInput;
import main.cards.Card;
import main.cards.Hero;

import java.util.ArrayList;

public class Player {
    private static final int MAX_MANA = 10;

    private int id;
    private int mana; // current mana
    private int nrWins;
    private int nrOfDecks;
    private Hero hero;
    private ArrayList<Card> hand; // list of cards in hand
    private ArrayList<Deck> decks; // player's decks of cards
    private Deck currentDeck; // currently used deck in the game

    private final DecksInput decksInput; // input info about decks

    public Player(final int id, final int nrWins, final int nrOfDecks,
                                final DecksInput decksInput) {
        this.id = id;
        this.mana = 1;
        this.nrWins = nrWins;
        this.nrOfDecks = nrOfDecks;
        this.hand = new ArrayList<>();
        this.decks = new ArrayList<>();
        this.decksInput = decksInput;

        initDecks();
    }

    /**
     * Initializes the player's decks using input data.
     */
    private void initDecks() {
        for (int i = 0; i < nrOfDecks; i++) {
            addDeck(decksInput.getDecks().get(i), id);
        }
    }

    /**
     * Adds a deck to the player's collection of decks.
     */
    public void addDeck(final ArrayList<CardInput> deckData, final int playerId) {
        decks.add(new Deck(deckData, playerId));
    }

    /**
     * Initializes the player's hero using input data.
     */
    public Hero initHero(final CardInput heroInput) {
        return new Hero(heroInput.getMana(), heroInput.getDescription(),
                heroInput.getColors(), heroInput.getName());
    }

    /**
     * Starts a new game for the player.
     */
    public void startGame(final int deckId, final int shuffleSeed, final CardInput heroInput) {
        setDeck(deckId); // Assign the deck to the player
        shuffleDeck(deckId, shuffleSeed); // Shuffle the deck
        this.hero = initHero(heroInput); // Set the hero for the player
        drawFirstCard(); // Player draws the first card at the start of the game
    }

    /**
     * Sets the player's current deck.
     */
    public void setDeck(final int index) {
        this.currentDeck = decks.get(index);
    }

    /**
     * Shuffles the specified deck using the given seed.
     */
    public void shuffleDeck(final int deckId, final int shuffleSeed) {
        decks.get(deckId).shuffle(shuffleSeed);
    }

    /**
     * Draws the first card from the current deck.
     */
    public void drawFirstCard() {
        Card drawnCard = currentDeck.drawCard();
        if (drawnCard != null) {
            hand.add(drawnCard);
        }
    }

    /**
     * Advances the player to the next round.
     */
    public void advanceToNextRound(final int manaToReceive) {
        // Draw a card if there are any left in the deck
        if (this.currentDeck.nrOfCardsinDeck > 0) {
            Card drawnCard = this.currentDeck.drawCard();
            if (drawnCard != null) {
                this.hand.add(drawnCard); // Add the drawn card to the hand
            }
        }

        // Increase the mana (ensure it doesn't exceed MAX_MANA = 10)
        this.mana += Math.min(manaToReceive, MAX_MANA);

        // Unfreeze the hero if it was frozen from the last round
        this.hero.setFrozen(false);
    }

    /**
     * Checks if the player has enough mana to play a card.
     */
    public boolean hasMana(final Card card) {
        return this.mana >= card.getMana();
    }

    /**
     * Gets the player's current mana.
     */
    public int getMana() {
        return mana;
    }

    /**
     * Sets the player's current mana.
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * Gets the player's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the player's hand.
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * Gets the player's decks.
     */
    public ArrayList<Deck> getDecks() {
        return decks;
    }

    /**
     * Gets the player's hero.
     */
    public Hero getHero() {
        return hero;
    }

    /**
     * Gets the player's current deck.
     */
    public Deck getCurrentDeck() {
        return currentDeck;
    }

    /**
     * Retrieves a card at a specific index in the hand.
     */
    public Card getCardFromHand(final int index) {
        if (index >= 0 && index < hand.size()) {
            return hand.get(index);
        }
        return null;
    }

    /**
     * Decreases the player's mana by the specified amount.
     */
    public void decreaseMana(final int nr) {
        this.mana -= nr;
    }

    /**
     * Resets the player's attributes for a new game.
     */
    public void resetPlayer() {
        this.mana = 1;
        this.hand.clear();
        this.currentDeck = null;
    }
}
