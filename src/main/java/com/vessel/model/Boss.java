package com.vessel.model;

import com.vessel.cards.Card;
import com.vessel.strategy.EnemyBehavior;

/**
 * The strongest enemy in a region. A boss fights with its normal behavior until it drops to
 * half health, then becomes enraged and switches to a second behavior for the rest of the fight.
 */
public class Boss extends Enemy {
    private final EnemyBehavior enragedBehavior;

    public Boss(String name, int maxHealth, EnemyBehavior normalBehavior,
                EnemyBehavior enragedBehavior, Card possessionCard) {
        super(name, maxHealth, normalBehavior, possessionCard);
        this.enragedBehavior = enragedBehavior;
    }

    /** True once the boss is at or below half of its maximum health. */
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
