package org.poo.main.cards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;

/**
 * Represents a generic card in the game.
 * Subclasses may extend this class to define specific card types
 * (Minion, SpecialCard, Hero).
 */
public class Card {
    protected int mana;
    protected int attackDamage;
    protected int health;
    protected String description;
    protected ArrayList<String> colors;
    protected String name;

    @JsonIgnore
    protected int hasUsedAttack;

    public Card(final int mana, final int attackDamage, final int health, final String description,
                final ArrayList<String> colors, final String name) {
        this.mana = mana;
        this.attackDamage = attackDamage;
        this.health = health;
        this.description = description;
        this.colors = colors;
        this.name = name;
        this.hasUsedAttack = 0;
    }

    /**
     * Returns the mana cost of the card.
     */
    public int getMana() {
        return mana;
    }

    /**
     * Sets the mana cost of the card.
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * Returns the attack damage of the card.
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Sets the attack damage of the card.
     */
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * Returns the health of the card.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health of the card.
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * Returns the description of the card.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the card.
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Returns the colors of the card.
     */
    public ArrayList<String> getColors() {
        return colors;
    }

    /**
     * Sets the colors of the card.
     */
    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    /**
     * Returns the name of the card.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the card.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Decreases the health of the card by a specified number of points.
     */
    public void decreaseHealth(final int points) {
        this.health -= points;
    }

    /**
     * Increases the health of the card by a specified number of points.
     */
    public void increaseHealth(final int points) {
        this.health += points;
    }

    /**
     * Returns whether the card has used its attack.
     */
    @JsonIgnore
    public int getHasUsedAttack() {
        return this.hasUsedAttack;
    }

    /**
     * Sets whether the card has used its attack.
     */
    @JsonIgnore
    public void setHasUsedAttack(final int hasUsedAttack) {
        this.hasUsedAttack = hasUsedAttack;
    }

    /**
     * Swaps the health and attack damage of the card.
     */
    public void swapHealthWithAttackDamage() {
        final int temp = this.health;
        this.health = this.attackDamage;
        this.attackDamage = temp;
    }

    /**
     * Increases the attack damage of the card by a specified number of points.
     */
    public void increaseAttackDamage(final int points) {
        this.attackDamage += points;
    }

    /**
     * Decreases the attack damage of the card by a specified number of points.
     */
    @JsonIgnore
    public void decreaseAttackDamage(final int points) {
        this.attackDamage = Math.max(this.attackDamage - points, 0);
    }

    /**
     * Returns whether the card is a tank. Subclasses may override this method.
     */
    @JsonIgnore
    public boolean isTank() {
        return false;
    }

    /**
     * Sets whether the card is frozen. Subclasses may override this method.
     */
    @JsonIgnore
    public void setFrozen(final boolean frozen) {
    }

    /**
     * Returns whether the card is frozen. Subclasses may override this method.
     */
    @JsonIgnore
    public boolean isFrozen() {
        return false;
    }
}
