package org.poo.main.cards;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class SpecialCard extends Card {

    @JsonIgnore
    private boolean frozen;
    @JsonIgnore
    private String ability;

    public SpecialCard(final int mana, final String description, final ArrayList<String> colors,
                       final String name, final int health, final int attackDamage) {
        super(mana, attackDamage, health, description, colors, name);
        this.frozen = false;

        // Assigning abilities based on the name
        switch (this.name) {
            case "The Ripper":
                this.ability = "Weak Knees";
                break;
            case "Miraj":
                this.ability = "Skyjack";
                break;
            case "The Cursed One":
                this.ability = "Shapeshift";
                break;
            case "Disciple":
                this.ability = "God's Plan";
                break;
            default:
                throw new IllegalArgumentException("Unknown card name: " + this.name);
        }
    }

    /**
     * Sets the health of the card.
     */
    public final void setHealth(final int health) {
        this.health = health;
    }

    /**
     * Sets whether the card is frozen.
     */
    @Override
    @JsonIgnore
    public final void setFrozen(final boolean frozen) {
        this.frozen = frozen;
    }

    /**
     * Sets the attack damage of the card.
     */
    public final void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * Returns the health of the card.
     */
    public final int getHealth() {
        return health;
    }

    /**
     * Returns whether the card is frozen.
     */
    @Override
    @JsonIgnore
    public final boolean isFrozen() {
        return frozen;
    }

    /**
     * Returns the attack damage of the card.
     */
    public final int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Returns the ability of the card.
     */
    public final String getAbility() {
        return ability;
    }

    /**
     * Checks whether the card is a tank.
     * By default, special cards are not tanks.
     */
    @Override
    @JsonIgnore
    public final boolean isTank() {
        return false;
    }
}
