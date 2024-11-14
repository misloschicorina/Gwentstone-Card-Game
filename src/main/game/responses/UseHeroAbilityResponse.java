package main.game.responses;

import fileio.Coordinates;

public class UseHeroAbilityResponse {
    private final String command;
    private int affectedRow;
    private final String error;

    // Constructor
    public UseHeroAbilityResponse (final String command, int affectedRow, final String error) {
        this.command = command;
        this.affectedRow = affectedRow;
        this.error = error;
    }

    public String getCommand() {
        return command;
    }

    public int getAffectedRow() { return affectedRow; }

    public String getError() { return error; }
}

