package main.game.responses;

public class GameEndedResponse {
    private final String gameEnded;

    // Constructor
    public GameEndedResponse(final String gameEnded) {
        this.gameEnded = gameEnded;
    }

    public String getGameEnded() {
        return gameEnded;
    }
}

