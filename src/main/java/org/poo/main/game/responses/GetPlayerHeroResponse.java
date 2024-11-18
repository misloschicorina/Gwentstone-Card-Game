package org.poo.main.game.responses;

import org.poo.main.cards.Hero;

public final class GetPlayerHeroResponse {

    private final String command;
    private final int playerId;
    private final Hero output;

    public GetPlayerHeroResponse(final String command, final Hero hero, final int playerId) {
        this.command = command;
        this.output = hero;
        this.playerId = playerId;
    }

    /**
     * Gets the command that generated the response.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the Hero object representing the player's hero.
     */
    public Hero getOutput() {
        return output;
    }

    /**
     * Gets the player ID for the hero being fetched.
     */
    public int getPlayerIdx() {
        return playerId;
    }
}
