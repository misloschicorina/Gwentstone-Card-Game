package org.poo.main.game.responses;

public final class GameStatsResponse {

    private final String command;
    private final int output;

    public GameStatsResponse(final String command, final int output) {
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
     * Gets the statistic value of this response.
     */
    public int getOutput() {
        return output;
    }
}
