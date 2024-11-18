package org.poo.main.game.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "command", "playerIdx", "output" })
public final class GetPlayerManaResponse {

    private final String command;

    @JsonProperty("playerIdx")
    private final int playerId;

    private final int output;

    public GetPlayerManaResponse(final String command, final int playerId, final int output) {
        this.command = command;
        this.playerId = playerId;
        this.output = output;
    }

    /**
     * Gets the command that generated this response.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the ID of the player whose mana is being fetched.
     */
    public int getPlayerIdx() {
        return playerId;
    }

    /**
     * Gets the player's current mana.
     */
    public int getOutput() {
        return output;
    }
}
