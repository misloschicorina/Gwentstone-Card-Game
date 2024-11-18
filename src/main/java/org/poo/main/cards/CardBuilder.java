package org.poo.main.cards;


import org.poo.fileio.CardInput;

import java.util.ArrayList;

/**
 * Utility class for creating Card objects based on input data.
 */
public final class CardBuilder {

    private CardBuilder() {
    }

    /**
     * Creates a card based on the input data and the owner ID.
     */
    public static Card createCard(final CardInput input, final int ownerId) {
        String name = input.getName();
        int mana = input.getMana();
        String description = input.getDescription();
        ArrayList<String> colors = input.getColors();
        int health = input.getHealth();
        int attackDamage = input.getAttackDamage();

        switch (name) {
            case "Goliath":
            case "Warden":
            case "Sentinel":
            case "Berserker":
                // Return Minion cards
                return new Minion(mana, description, colors, name, health, attackDamage);
            case "The Ripper":
            case "Miraj":
            case "The Cursed One":
            case "Disciple":
                // Return SpecialCard cards
                return new SpecialCard(mana, description, colors, name, health, attackDamage);
            default:
                return null;
        }
    }
}
