package main.cards;

import fileio.CardInput;

import java.util.ArrayList;

public class CardBuilder {

    public static Card createCard(CardInput input, int ownerId) {
        String name = input.getName();
        int mana = input.getMana();
        String description = input.getDescription();
        ArrayList<String> colors = input.getColors();
        int health = input.getHealth();
        int attackDamage = input.getAttackDamage();

        // Initial position coordinates x, y set to -1, as cards are considered off the game board
        int x = -1;
        int y = -1;

        switch (name) {
            case "Goliath":
            case "Warden":
            case "Sentinel":
            case "Berserker":
                // Return Minion cards
                return new Minion(mana, description, colors, name, health, attackDamage, x, y);
            case "The Ripper":
            case "Miraj":
            case "The Cursed One":
            case "Disciple":
                // Return SpecialCard cards
                return new SpecialCard(mana, description, colors, name, health, attackDamage, x, y);
            default:
                return null; // Return null if the card name is unknown
        }
    }
}
