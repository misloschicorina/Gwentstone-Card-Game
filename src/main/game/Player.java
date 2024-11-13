package main.game;

import fileio.DecksInput;
import fileio.CardInput;
import main.cards.Card;
import main.cards.Hero;

import java.util.ArrayList;

public class Player {

    public int id; // 1 sau 2
    public int mana; // mana curenta

    public int nrWins;
    public int nrLost;

    public int nrOfDecks;

    public Hero hero;

    public ArrayList<Card> hand; // lista de carti din mana
    public ArrayList<Deck> decks;  // deck-urile de carti ale player-ului

    public Deck currentDeck; // deck ul curent folosit in joc

    private final DecksInput decksInput; // info despre deck-urile primite in input

    // Constructor
    public Player(int id, int nrWins, int nrLost, int nrOfDecks, DecksInput decksInput) {
        this.id = id;
        this.mana = 1;
        this.nrWins = nrWins;
        this.nrLost = nrLost;
        this.nrOfDecks = nrOfDecks;
        this.hand = new ArrayList<>();
        this.decks = new ArrayList<>();
        this.decksInput = decksInput;

        initDecks();
    }

    // Initialize player's decks
    private void initDecks() {
        for (int i = 0; i < nrOfDecks; i++) {
            addDeck(decksInput.getDecks().get(i), id);
        }
    }

    // Add a deck to player's decks
    public void addDeck(final ArrayList<CardInput> deck, int id) {
        decks.add(new Deck(deck, id));
    }

    public Hero initHero(CardInput hero) {
        return new Hero(hero.getMana(), hero.getDescription(), hero.getColors(), hero.getName());
    }


    // Method to start a new game
    public void startGame(int deckId, int shuffleSeed, CardInput hero) {
        setDeck(deckId); // Atribuie deck ul playerului
        shuffleDeck(deckId, shuffleSeed); // Shuffle the deck
        this.hero = initHero(hero); // Set the hero for the player
        drawFirstCard(); // player ul trage o carte in mana la inceput de joc
    }

    public void setDeck(int index) {
        this.currentDeck = decks.get(index);
    }

    public void shuffleDeck(int deckId, int shuffleSeed) {
        decks.get(deckId).shuffle(shuffleSeed);
    }

    public void drawFirstCard() {
        Card drawnCard = currentDeck.drawCard();
        if (drawnCard != null) {
            hand.add(drawnCard);
        }
    }

    public void advanceToNextRound(int manaToReceive) {
        // Draw a card if there are any left in the deck
        if (this.currentDeck.nrOfCardsinDeck > 0) {
            Card drawnCard = this.currentDeck.drawCard();
            if (drawnCard != null) {
                this.hand.add(drawnCard);  // Add the drawn card to the hand
            }
        }

        // Increase the mana (ensure it doesn't exceed 10)
        this.mana += Math.min(manaToReceive, 10);

        // Unfreeze the hero if it was frozen from the last round
        this.hero.setFrozen(false);
    }

    // Check if the player has enough mana to place a card
    public boolean hasMana(Card card) {
        return this.mana >= card.getMana();
    }

    // Getters and setters for various attributes
    public final int getMana() {
        return mana;
    }

    public final void setMana(int mana) {
        this.mana = mana;
    }

    public final int getNrWins() {
        return nrWins;
    }

    public final void setNrWins(int nrWins) {
        this.nrWins = nrWins;
    }

    public final int getNrLost() {
        return nrLost;
    }

    public final void setNrLost(int nrLost) {
        this.nrLost = nrLost;
    }

    public final ArrayList<Card> getHand() {
        return hand;
    }

    public final ArrayList<Deck> getDecks() {
        return decks;
    }

    public final Hero getHero() {
        return hero;
    }

    public final Deck getCurrentDeck() {
        return currentDeck;
    }

    // get the card at a specific idx in the hand
    public Card getCardFromHand(int index) {
        if (index >= 0 && index < hand.size()) {
            return hand.get(index);
        }
        return null;
    }

    public void decreaseMana(int nr) {
        this.mana -= nr;
    }

    public int getId()  { return this.id; }

}
