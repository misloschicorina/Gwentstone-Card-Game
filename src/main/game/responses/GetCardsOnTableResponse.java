package main.game.responses;

import main.cards.Card;

import java.util.ArrayList;

public final class GetCardsOnTableResponse {

    private final String command;
    private final ArrayList<ArrayList<Card>> output;

    public GetCardsOnTableResponse(final String command, final ArrayList<ArrayList<Card>> output) {
        this.command = command;
        this.output = output;
    }

    /**
     * Gets the command associated with this response.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the list of cards on the table.
     */
    public ArrayList<ArrayList<Card>> getOutput() {
        return output;
    }
}
