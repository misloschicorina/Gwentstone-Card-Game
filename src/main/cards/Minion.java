package main.cards;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Minion extends Card {
    @JsonIgnore
    private boolean frozen;

    // Minion's coords on gameboard
    @JsonIgnore
    private int x;
    @JsonIgnore
    private int y;

    public Minion(int mana, String description, ArrayList<String> colors, String name,
                  int health, int attackDamage, int x, int y) {
        super(mana, attackDamage, health, description, colors, name);
        this.frozen = false;
        this.x = x;
        this.y = y;
    }

    // Setters:

    public void setHealth(int health) { this.health = health; }

    @Override
    @JsonIgnore
    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }


    public void setAttackDamage(int attackDamage) { this.attackDamage = attackDamage; }

    // Getters:

    public int getHealth() { return health; }

    @Override
    @JsonIgnore
    public boolean isFrozen() { return frozen; }

    public int getAttackDamage() { return attackDamage; }

    @Override
    @JsonIgnore
    public boolean isTank() {
        return this.name.equals("Goliath") || this.name.equals("Warden");
    }

}
