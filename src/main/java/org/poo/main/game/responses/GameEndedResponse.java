package org.poo.main.game.responses;

public final class GameEndedResponse {

    private final String gameEnded;

    public GameEndedResponse(final String gameEnded) {
        this.gameEnded = gameEnded;
    }

    /**
     * Gets the message indicating the end of the game.
     */
    public String getGameEnded() {
        return gameEnded;
    }
}
