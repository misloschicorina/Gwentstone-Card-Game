package main.cards;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Minion extends Card {

    @JsonIgnore
    private boolean frozen;

    public Minion(final int mana, final String description, final ArrayList<String> colors,
                  final String name, final int health, final int attackDamage) {
        super(mana, attackDamage, health, description, colors, name);
        this.frozen = false;
    }

    /**
     * Sets the health of the minion.
     */
    public final void setHealth(final int health) {
        this.health = health;
    }

    /**
     * Sets whether the minion is frozen.
     */
    @Override
    @JsonIgnore
    public final void setFrozen(final boolean frozen) {
        this.frozen = frozen;
    }

    /**
     * Sets the attack damage of the minion.
     */
    public final void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * Returns the health of the minion.
     */
    public final int getHealth() {
        return health;
    }

    /**
     * Returns whether the minion is frozen.
     */
    @Override
    @JsonIgnore
    public final boolean isFrozen() {
        return frozen;
    }

    /**
     * Returns the attack damage of the minion.
     */
    public final int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Checks whether the minion is a tank.
     */
    @Override
    @JsonIgnore
    public final boolean isTank() {
        return "Goliath".equals(this.name) || "Warden".equals(this.name);
    }
}
