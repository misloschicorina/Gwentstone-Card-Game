package main.game.responses;

import main.cards.Card;

import java.util.ArrayList;

public final class GetPlayerDeckResponse {

    private final String command;
    private final ArrayList<Card> output;
    private final int playerId;

    public GetPlayerDeckResponse(final String command, final ArrayList<Card> deck,
                                                                final int playerId) {
        this.command = command;
        this.output = deck;
        this.playerId = playerId;
    }

    /**
     * Gets the command associated with this response.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the deck of cards for the player.
     */
    public ArrayList<Card> getOutput() {
        return output;
    }

    /**
     * Gets the ID of the player.
     */
    public int getPlayerIdx() {
        return playerId;
    }
}
