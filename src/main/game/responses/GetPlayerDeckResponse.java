package main.game.responses;

import main.cards.Card;

import java.util.ArrayList;

public class GetPlayerDeckResponse {

    private final String command;

    private final ArrayList<Card> output;
    private final int playerIdx;

    public GetPlayerDeckResponse(final String command, final ArrayList<Card> deck, final int playerIdx) {
        this.command = command;
        this.output = deck;
        this.playerIdx = playerIdx;
    }

    public final String getCommand() {
        return command;
    }

    public final ArrayList<Card> getOutput() {
        return output;
    }

    public final int getPlayerIdx() {
        return playerIdx;
    }

}

