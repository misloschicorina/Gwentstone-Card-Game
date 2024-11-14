package main.game.responses;

public class GameStatsResponse {
    private String command;
    private int output;

    public GameStatsResponse(String command, int output) {
        this.command = command;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public int getOutput() {
        return output;
    }
}

