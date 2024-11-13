package main.game.responses;

public class GetCardAtPositionInvalidResponse {
    private final String command;
    private final int x;
    private final int y;
    private final String output;

    public GetCardAtPositionInvalidResponse(final String command, final int x, final int y, final String output) {
        this.command = command;
        this.output = output;  // Will be the error message
        this.x = x;
        this.y = y;
    }

    public String getCommand() {
        return command;
    }

    public String getOutput() {
        return output;  // This will return the error message
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
