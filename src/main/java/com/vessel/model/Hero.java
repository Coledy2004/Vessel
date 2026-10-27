package com.vessel.model;

import com.vessel.cards.Deck;


public class Hero extends Entity {
    public static final int ENERGY_PER_TURN = 3;

    private final Deck deck;

    public Hero(String name, int maxHealth, Deck deck) {
        super(name, maxHealth);
        this.deck = deck;
    }

    public Deck getDeck() {
        return deck;
    }
}
