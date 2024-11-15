package main.game.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetTotalGamesPlayedResponse {
    private final String command;

    private final int output;

    public GetTotalGamesPlayedResponse(final String command, final int output) {
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
