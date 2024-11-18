package main.game.responses;

public final class UseHeroAbilityResponse {
    private final String command;
    private final int affectedRow;
    private final String error;

    public UseHeroAbilityResponse(final String command, final int affectedRow, final String error) {
        this.command = command;
        this.affectedRow = affectedRow;
        this.error = error;
    }

    /**
     * Gets the command associated with this response.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the row affected by the hero's ability.
     */
    public int getAffectedRow() {
        return affectedRow;
    }

    /**
     * Gets the error message associated with this response.
     */
    public String getError() {
        return error;
    }
}
