package com.vessel.model;

import com.vessel.cards.Deck;

/**
 * The player character. Each enemy the hero possesses costs some humanity;
 * at zero humanity the hero has fully lost themselves.
 */
public class Hero extends Entity {
    public static final int STARTING_HUMANITY = 100;
    public static final int ENERGY_PER_TURN = 3;

    private final Deck deck;
    private int humanity = STARTING_HUMANITY;

    public Hero(String name, int maxHealth, Deck deck) {
        super(name, maxHealth);
        this.deck = deck;
    }

    public Deck getDeck() {
        return deck;
    }

    public int getHumanity() {
        return humanity;
    }

    public void loseHumanity(int amount) {
        humanity = Math.max(0, humanity - amount);
    }

    public boolean isLost() {
        return humanity == 0;
    }
}
