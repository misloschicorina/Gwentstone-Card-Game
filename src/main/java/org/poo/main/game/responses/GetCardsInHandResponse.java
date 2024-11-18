package org.poo.main.game.responses;

import org.poo.main.cards.Card;

import java.util.ArrayList;

public final class GetCardsInHandResponse {

    private final String command;
    private final ArrayList<Card> output;
    private final int playerId;

    public GetCardsInHandResponse(final String command, final ArrayList<Card> output,
                                                                final int playerId) {
        this.command = command;
        this.output = output;
        this.playerId = playerId;
    }

    /**
     * Gets the command that generated the response.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the list of cards in the player's hand.
     */
    public ArrayList<Card> getOutput() {
        return output;
    }

    /**
     * Gets the player ID for the cards in hand.
     */
    public int getPlayerIdx() {
        return playerId;
    }
}
