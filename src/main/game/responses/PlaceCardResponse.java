package main.game.responses;

public class PlaceCardResponse {

    private final String command;
    private final int handIdx;
    private final String error;

    // Constructor
    public PlaceCardResponse(final String command, final int handIdx, final String error) {
        this.command = command;
        this.handIdx = handIdx;
        this.error = error;
    }

    public String getCommand() {
        return command;
    }

    public int getHandIdx() {
        return handIdx;
    }

    public String getError() {
        return error;
    }
}
