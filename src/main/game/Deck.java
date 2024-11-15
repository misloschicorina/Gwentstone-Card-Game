package main.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import fileio.CardInput;

import main.cards.Card;
import main.cards.CardBuilder;


public class Deck {
    public ArrayList<Card> cards;  // deck ul e un array de obiecte Card
    public int nrOfCardsinDeck;

    // Constructor that takes an ArrayList<CardInput> to create the deck of a player
    public Deck(ArrayList<CardInput> cardsInput, int ownerId) {
        this.cards = new ArrayList<>();
        this.nrOfCardsinDeck = cardsInput.size();
        for (int i = 0; i < cardsInput.size(); i++) {

            // Parse the data from the JSON input file
            CardInput cardInput = cardsInput.get(i);

            // Create a card using the CardBuilder
            Card card = CardBuilder.createCard(cardInput, ownerId);

            // Adding the created card to the cards array
            this.cards.add(card);
        }
    }

    public ArrayList<Card> getCardsfromDeck() {
        return cards;
    }

    public int getNumberOfCardsInDeck() {
        return nrOfCardsinDeck;
    }

    public Card getCard(int index) {
        return cards.get(index);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            return null; // Return null if no cards are left in the deck
        }
        nrOfCardsinDeck--;
        return cards.remove(0); // Remove the top card from the deck
    }


    public void shuffle(int seed) {
        Random random = new Random(seed);
        Collections.shuffle(this.cards, random);
    }
}
