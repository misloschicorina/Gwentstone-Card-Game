package main.game.responses;

public final class GetPlayerTurnResponse {

    private final String command;
    private final int output;

    public GetPlayerTurnResponse(final String command, final int output) {
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
     * Gets the output value representing the player's turn.
     */
    public int getOutput() {
        return output;
    }
}
