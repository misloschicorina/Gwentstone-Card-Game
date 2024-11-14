package main.game.responses;
import java.util.ArrayList;
import main.cards.Card;

import java.util.ArrayList;

public class GetFrozenCardsResponse {
    private String command;
    private ArrayList<Card> output;

    // Constructor
    public GetFrozenCardsResponse(String command, ArrayList<Card> output) {
        this.command = command;
        this.output = output;
    }

    // Getter for command
    public String getCommand() {
        return command;
    }

    // Getter for output (frozen cards list)
    public ArrayList<Card> getOutput() {
        return output;
    }

    // Override toString() for easy display (optional)
    @Override
    public String toString() {
        return "GetFlatFrozenCardsResponse{" +
                "command='" + command + '\'' +
                ", output=" + output +
                '}';
    }
}
