package main.game.responses;

public final class PlaceCardResponse {

    private final String command;
    private final int handIdx;
    private final String error;

    public PlaceCardResponse(final String command, final int handIdx, final String error) {
        this.command = command;
        this.handIdx = handIdx;
        this.error = error;
    }

    /**
     * Gets the command associated with this response.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the index of the card in the player's hand.
     */
    public int getHandIdx() {
        return handIdx;
    }

    /**
     * Gets the error message associated with this response.
     */
    public String getError() {
        return error;
    }
}
