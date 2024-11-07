package main.game.responses;

public class GetPlayerTurnResponse {

    private final String command;
    private final int output;

    public GetPlayerTurnResponse(final String command, final int output) {
        this.command = command;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public int getOutput() {
        return output;
    }
}