package com.vessel.model;

/**
 * Anything that can fight: the hero or an enemy.
 * Holds health and block (temporary shield that absorbs damage this turn).
 */
public abstract class Entity {
    private final String name;
    private final int maxHealth;
    private int health;
    private int block;

    protected Entity(String name, int maxHealth) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    /** Applies damage, letting block absorb it first. Returns damage actually taken to health. */
    public int takeDamage(int amount) {
        if (amount <= 0) {
            return 0;
        }
        int absorbed = Math.min(block, amount);
        block -= absorbed;
        int remaining = amount - absorbed;
        int taken = Math.min(health, remaining);
        health -= taken;
        return taken;
    }

    public void gainBlock(int amount) {
        block += Math.max(0, amount);
    }

    public void clearBlock() {
        block = 0;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getBlock() {
        return block;
    }

    @Override
    public String toString() {
        return name + " (" + health + "/" + maxHealth + " HP, " + block + " block)";
    }
}
