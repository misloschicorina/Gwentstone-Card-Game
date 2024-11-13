package main.game.responses;

import main.cards.Card;

import java.util.ArrayList;

public class GetCardsInHandResponse {
    private final String command;

    private final ArrayList<Card> output;
    private final int playerId;

    public GetCardsInHandResponse(final String command, final ArrayList<Card> output, final int playerId) {
        this.command = command;
        this.output = output;
        this.playerId = playerId;
    }

    public final String getCommand() {
        return command;
    }

    public final ArrayList<Card> getOutput() {
        return output;
    }

    public final int getPlayerIdx() {
        return playerId;
    }

}
