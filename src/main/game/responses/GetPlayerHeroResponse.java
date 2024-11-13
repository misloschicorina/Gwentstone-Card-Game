package main.game.responses;

import main.cards.Hero;

public class GetPlayerHeroResponse {

    private final String command;
    private final int playerId;

    private final Hero output;


    public GetPlayerHeroResponse(final String command, final Hero hero, final int playerId) {
        this.command = command;
        this.output = hero;
        this.playerId = playerId;
    }

    public final String getCommand() {
        return command;
    }

    public final Hero getOutput() {
        return output;
    }

    public final int getPlayerIdx() {
        return playerId;
    }

}
