package main.game.responses;

import main.cards.Hero;

public class GetPlayerHeroResponse {

    private final String command;
    private final int playerIdx;

    private final Hero output;


    public GetPlayerHeroResponse(final String command, final Hero hero, final int playerIdx) {
        this.command = command;
        this.output = hero;
        this.playerIdx = playerIdx;
    }

    public final String getCommand() {
        return command;
    }

    public final Hero getOutput() {
        return output;
    }

    public final int getPlayerIdx() {
        return playerIdx;
    }

}
