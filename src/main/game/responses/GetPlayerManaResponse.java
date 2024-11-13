package main.game.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "command", "playerIdx", "output" })
public class GetPlayerManaResponse {

    private final String command;

    @JsonProperty("playerIdx")
    private final int playerId;

    private final int output;

    public GetPlayerManaResponse(final String command, final int playerId, final int output) {
        this.command = command;
        this.playerId = playerId;
        this.output = output;
    }

    public final String getCommand() {
        return command;
    }

    public final int getPlayerIdx() {
        return playerId;
    }

    public final int getOutput() {
        return output;
    }

}
