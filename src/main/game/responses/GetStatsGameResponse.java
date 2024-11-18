package main.game.responses;

public class GetStatsGameResponse {
    private final String command;

    private final int output;

    public GetStatsGameResponse(final String command, final int output) {
        this.command = command;
        this.output = output;
    }

    public final String getCommand() {
        return command;
    }


    public final int getOutput() {
        return output;
    }


}
