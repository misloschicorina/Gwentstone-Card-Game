package main.game.responses;
import fileio.*;

public class CardUsesAttackResponse {
    private final String command;
    private final Coordinates cardAttacked;  // Adăugăm coordonatele atacatorului
    private final Coordinates cardAttacker;  // Adăugăm coordonatele atacatului
    private final String error;

    // Constructor
    public CardUsesAttackResponse(final String command, final Coordinates cardAttacked,
                                  final Coordinates cardAttacker, final String error) {
        this.command = command;
        this.cardAttacked = cardAttacked;
        this.cardAttacker = cardAttacker;
        this.error = error;
    }

    public String getCommand() {
        return command;
    }

    public Coordinates getCardAttacker() {
        return cardAttacker;
    }

    public Coordinates getCardAttacked() {
        return cardAttacked;
    }

    public String getError() {
        return error;
    }
}
