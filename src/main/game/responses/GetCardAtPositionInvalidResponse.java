package main.game.responses;

public final class GetCardAtPositionInvalidResponse {
    private final String command;
    private final int x;
    private final int y;
    private final String output;

    public GetCardAtPositionInvalidResponse(final String command, final int x, final int y,
                                                                        final String output) {
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
     * Gets the error message.
     */
    public String getOutput() {
        return output;
    }

    /**
     * Gets the x-coordinate of the invalid position.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the invalid position.
     */
    public int getY() {
        return y;
    }
}
