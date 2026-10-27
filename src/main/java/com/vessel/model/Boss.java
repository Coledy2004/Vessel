package com.vessel.model;

import com.vessel.cards.Card;
import com.vessel.strategy.EnemyBehavior;

public class Boss extends Enemy {
    private final EnemyBehavior enragedBehavior;

    public Boss(String name, int maxHealth, EnemyBehavior normalBehavior,
                EnemyBehavior enragedBehavior, Card possessionCard) {
        super(name, maxHealth, normalBehavior, possessionCard);
        this.enragedBehavior = enragedBehavior;
    }

    public boolean isEnraged() {
        return getHealth() <= getMaxHealth() / 2;
    }

    @Override
    protected EnemyBehavior currentBehavior() {
        return isEnraged() ? enragedBehavior : super.currentBehavior();
    }

    @Override
    public String getIntent() {
        return isEnraged() ? "ENRAGED! " + super.getIntent() : super.getIntent();
    }
}
