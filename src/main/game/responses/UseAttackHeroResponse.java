package main.game.responses;
import fileio.Coordinates;

public final class UseAttackHeroResponse {
    private final String command;
    private final Coordinates cardAttacker;
    private final String error;

    public UseAttackHeroResponse(final String command, final Coordinates cardAttacker,
                                                                final String error) {
        this.command = command;
        this.cardAttacker = cardAttacker;
        this.error = error;
    }

    /**
     * Gets the command associated with this response.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the coordinates of the card attacking the hero.
     */
    public Coordinates getCardAttacker() {
        return cardAttacker;
    }

    /**
     * Gets the error message associated with this response.
     */
    public String getError() {
        return error;
    }
}
