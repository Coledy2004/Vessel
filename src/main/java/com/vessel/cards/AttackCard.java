package com.vessel.cards;

import com.vessel.model.Entity;

public class AttackCard implements Card {
    private final String name;
    private final int cost;
    private final int damage;

    public AttackCard(String name, int cost, int damage) {
        this.name = name;
        this.cost = cost;
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public String getDescription() {
        return "Deal " + damage + " damage.";
    }

    @Override
    public String play(Entity user, Entity target) {
        int taken = target.takeDamage(damage);
        return user.getName() + " plays " + name + ": " + target.getName() + " takes " + taken + " damage.";
    }
}
