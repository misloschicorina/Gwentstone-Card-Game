package main.game.responses;
import fileio.*;

public class UseAttackHeroResponse {
    private final String command;
    private final Coordinates cardAttacker;  // Adăugăm coordonatele atacatului
    private final String error;

    // Constructor
    public UseAttackHeroResponse(final String command, final Coordinates cardAttacker, final String error) {
        this.command = command;
        this.cardAttacker = cardAttacker;
        this.error = error;
    }

    public String getCommand() {
        return command;
    }

    public Coordinates getCardAttacker() {
        return cardAttacker;
    }

    public String getError() {
        return error;
    }
}

