package main.cards;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Hero extends Card {

    // Constanta de inceput pentru sanatatea eroului:
    @JsonIgnore
    public final int STARTING_HEALTH = 30;

    public int health; // sanatate curenta erou

    @JsonIgnore
    public boolean frozen;
    @JsonIgnore
    public String ability;

    @JsonIgnore
    public int attackDamage;

    public Hero(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, 0, 30, description, colors, name);
        this.frozen = false;
        this.health = STARTING_HEALTH;
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
        }
    }

    // Setters:
    public final void setHealth(int health) {
        this.health = health;
    }

    @JsonIgnore
    public final void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    // Getters:
    public final int getHealth() {
        return health;
    }

    @JsonIgnore
    public boolean isFrozen() {
        return frozen;
    }

    public String getAbility() {
        return ability;
    }
}
