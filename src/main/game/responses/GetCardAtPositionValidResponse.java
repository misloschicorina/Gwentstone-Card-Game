package main.game.responses;

import main.cards.Card;

public final class GetCardAtPositionValidResponse {
    private final String command;
    private final int x;
    private final int y;
    private final Card output;

    public GetCardAtPositionValidResponse(final String command, final int x, final int y,
                                                                        final Card output) {
        this.command = command;
        this.x = x;
        this.y = y;
        this.output = output;
    }

    /**
     * Gets the command associated with this response.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the card at the specified position.
     */
    public Card getOutput() {
        return output;
    }

    /**
     * Gets the x-coordinate of the card.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the card.
     */
    public int getY() {
        return y;
    }
}
