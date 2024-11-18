package main.game.responses;

import main.cards.Card;
import java.util.ArrayList;

public final class GetFrozenCardsResponse {
    private final String command;
    private final ArrayList<Card> output;

    public GetFrozenCardsResponse(final String command, final ArrayList<Card> output) {
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
     * Gets the list of frozen cards.
     */
    public ArrayList<Card> getOutput() {
        return output;
    }
}
