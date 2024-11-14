package main.cards;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Card {
    protected int mana;

    protected int attackDamage;
    protected int health;
    protected String description;
    protected ArrayList<String> colors;
    protected String name;

    @JsonIgnore
    protected int hasUsedAttack;

    public Card(int mana, int attackDamage, int health, String description, ArrayList<String> colors, String name) {
        this.mana = mana;
        this.attackDamage = attackDamage;
        this.health = health;
        this.description = description;
        this.colors = colors;
        this.name = name;
        this.hasUsedAttack = 0;
    }

    // Getters:
    public int getMana() {
        return mana;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getHealth() {
        return health;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public String getName() {
        return name;
    }

    // Setters:
    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void decreaseHealth(int points) {
        this.health -= points;
    }

    public void increaseHealth(int points) {
        this.health += points;
    }

    public void decreaseAttackDamage(int points) {
        this.attackDamage -= points;
    }

    @JsonIgnore
    public int getHasUsedAttack() {
        return this.hasUsedAttack;
    }

    @JsonIgnore
    public void setHasUsedAttack(int hasUsedAttack) {
        this.hasUsedAttack = hasUsedAttack;
    }

    public void swapHealth(Card otherCard) {
        int tempHealth = this.health;
        this.health = otherCard.getHealth();
        otherCard.setHealth(tempHealth);
    }

    public void swapHealthWithAttackDamage() {
        int temp = this.health;
        this.health = this.attackDamage;
        this.attackDamage = temp;
    }

    public void increaseAttackDamage(int points) {
        this.attackDamage += points;
    }

    @JsonIgnore
    public boolean isTank() {
        if (this.name.equals("Goliath") || this.name.equals("Warden"))
            return true;
        return false;
    }
}
