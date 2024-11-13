package main.game.responses;

import main.cards.Card;

import java.util.ArrayList;

public class GetCardsOnTableResponse {

    private final String command;
    private final ArrayList<ArrayList<Card>> output;

    public GetCardsOnTableResponse(final String command, final ArrayList<ArrayList<Card>> output) {
        this.command = command;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public ArrayList<ArrayList<Card>> getOutput() {
        return output;
    }
}
