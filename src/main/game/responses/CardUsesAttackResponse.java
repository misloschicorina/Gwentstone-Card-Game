package main.game.responses;

import fileio.Coordinates;

public final class CardUsesAttackResponse {
    private final String command;
    private final Coordinates cardAttacked;
    private final Coordinates cardAttacker;
    private final String error;

    public CardUsesAttackResponse(final String command, final Coordinates cardAttacked,
                                  final Coordinates cardAttacker, final String error) {
        this.command = command;
        this.cardAttacked = cardAttacked;
        this.cardAttacker = cardAttacker;
        this.error = error;
    }

    /**
     * Gets the command name.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the coordinates of the attacking card.
     */
    public Coordinates getCardAttacker() {
        return cardAttacker;
    }

    /**
     * Gets the coordinates of the attacked card.
     */
    public Coordinates getCardAttacked() {
        return cardAttacked;
    }

    /**
     * Gets the error message, if any.
     */
    public String getError() {
        return error;
    }
}
