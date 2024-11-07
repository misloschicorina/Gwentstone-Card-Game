package main.game;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;


import main.cards.Card;
import main.cards.Hero;
import java.util.ArrayList;

import main.game.responses.*;

public class CommandExecutor {

    private final ObjectMapper objectMapper;
    private final ArrayNode output;

    // Constructor to match the GameEngine constructor
    public CommandExecutor(ObjectMapper objectMapper, ArrayNode output) {
        this.objectMapper = objectMapper;
        this.output = output;
    }

    public void getPlayerDeck(int playerId, ArrayList<Card> deck) {
        GetPlayerDeckResponse printDeck = new GetPlayerDeckResponse("getPlayerDeck", deck, playerId);
        output.add(objectMapper.convertValue(printDeck, JsonNode.class));
    }

    public void getPlayerHero(int playerId, Hero hero) {
        GetPlayerHeroResponse printHero = new GetPlayerHeroResponse("getPlayerHero", hero, playerId);
        output.add(objectMapper.convertValue(printHero, JsonNode.class));
    }

    public void getPlayerTurn(int playerTurn) {
        GetPlayerTurnResponse printTurn = new GetPlayerTurnResponse("getPlayerTurn", playerTurn);
        output.add(objectMapper.convertValue(printTurn, JsonNode.class));
    }
}
