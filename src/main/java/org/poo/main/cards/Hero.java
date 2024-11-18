package org.poo.main.cards;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Hero extends Card {
    private static final int STARTING_HEALTH = 30;

    private int health;
    @JsonIgnore
    private boolean frozen;
    @JsonIgnore
    private String ability;
    @JsonIgnore
    private int attackDamage;
    @JsonIgnore
    private int hasUsedAbility;

    public Hero(final int mana, final String description, final ArrayList<String> colors,
                                                                        final String name) {
        super(mana, 0, STARTING_HEALTH, description, colors, name);
        this.frozen = false;
        this.health = STARTING_HEALTH;
        this.hasUsedAbility = 0;

        switch (this.name) {
            case "Lord Royce":
                this.ability = "Sub-Zero";
                break;
            case "Empress Thorina":
                this.ability = "Low Blow";
                break;
            case "King Mudface":
                this.ability = "Earth Born";
                break;
            case "General Kocioraw":
                this.ability = "Blood Thirst";
                break;
            default:
                throw new IllegalArgumentException("Unknown hero name: " + this.name);
        }
    }

    /**
     * Returns the current health of the hero.
     */
    public final int getHealth() {
        return health;
    }

    /**
     * Sets the health of the hero.
     */
    public final void setHealth(final int health) {
        this.health = health;
    }

    /**
     * Returns the ability of the hero.
     */
    public String getAbility() {
        return ability;
    }

    /**
     * Decreases the hero's health by a specified number of points.
     */
    @Override
    public void decreaseHealth(final int points) {
        this.health -= points;
    }

    /**
     * Returns whether the hero has used its ability.
     */
    @JsonIgnore
    public int getHasUsedAbility() {
        return hasUsedAbility;
    }

    /**
     * Sets whether the hero has used its ability.
     */
    @JsonIgnore
    public void setHasUsedAbility(final int hasUsedAbility) {
        this.hasUsedAbility = hasUsedAbility;
    }

    /**
     * Resets the hero's ability usage.
     */
    @JsonIgnore
    public void resetHeroAbility() {
        this.hasUsedAbility = 0;
    }
}
