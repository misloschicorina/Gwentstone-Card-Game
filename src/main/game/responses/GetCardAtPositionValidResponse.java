package main.game.responses;

import main.cards.Card;

public class GetCardAtPositionValidResponse {
    private final String command;
    private final int x;
    private final int y;
    private final Card output;

    public GetCardAtPositionValidResponse(final String command, final int x, final int y, final Card output) {
        this.command = command;
        this.x = x;
        this.y = y;
        this.output = output;  // This will be the card object

    }

    public String getCommand() {
        return command;
    }

    public Card getOutput() {
        return output;  // Return the actual card object
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
