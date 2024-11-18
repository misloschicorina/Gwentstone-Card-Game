package org.poo.main.game;

import org.poo.fileio.CardInput;
import org.poo.main.cards.Card;
import org.poo.main.cards.CardBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
    protected ArrayList<Card> cards;
    protected int nrOfCardsinDeck;

    public Deck(final ArrayList<CardInput> cardsInput, final int ownerId) {
        this.cards = new ArrayList<>();
        this.nrOfCardsinDeck = cardsInput.size();
        for (int i = 0; i < cardsInput.size(); i++) {
            CardInput cardInput = cardsInput.get(i);
            Card card = CardBuilder.createCard(cardInput, ownerId);
            this.cards.add(card);
        }
    }

    /**
     * Returns the list of cards in the deck.
     */
    public ArrayList<Card> getCardsfromDeck() {
        return new ArrayList<>(cards);
    }

    /**
     * Returns the number of cards remaining in the deck.
     */
    public int getNumberOfCardsInDeck() {
        return nrOfCardsinDeck;
    }

    /**
     * Draws the top card from the deck. The card is removed from the deck.
     */
    public Card drawCard() {
        if (cards.isEmpty()) {
            return null;
        }
        nrOfCardsinDeck--;
        return cards.remove(0);
    }

    /**
     * Shuffles the deck using a specified seed.
     */
    public void shuffle(final int seed) {
        Random random = new Random(seed);
        Collections.shuffle(this.cards, random);
    }
}
