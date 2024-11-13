package main.cards;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class SpecialCard extends Card {
    @JsonIgnore
    private boolean frozen;
    @JsonIgnore
    private String ability;

    // Special Card's coords on gameboard
    @JsonIgnore
    private int x;
    @JsonIgnore
    private int y;

    public SpecialCard(int mana, String description, ArrayList<String> colors, String name,
                       int health, int attackDamage, int x, int y) {
        super(mana, attackDamage, health, description, colors, name);
        this.frozen = false;
        this.x = x;
        this.y = y;

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
        }
    }

    // Setters:
    public void setHealth(int health) {
        this.health = health;
    }

    @JsonIgnore
    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    // Getters:
    public int getHealth() {
        return health;
    }

    @JsonIgnore
    public boolean isFrozen() {
        return frozen;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public String getAbility() {
        return ability;
    }
}
