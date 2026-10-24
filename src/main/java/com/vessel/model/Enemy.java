package com.vessel.model;

import com.vessel.cards.Card;
import com.vessel.strategy.EnemyBehavior;

/**
 * A monster in the dungeon. How it fights is delegated to an {@link EnemyBehavior}
 * (Strategy pattern), and when defeated it can be possessed to gain its signature card.
 */
public class Enemy extends Entity {
    private final EnemyBehavior behavior;
    private final Card possessionCard;

    public Enemy(String name, int maxHealth, EnemyBehavior behavior, Card possessionCard) {
        super(name, maxHealth);
        this.behavior = behavior;
        this.possessionCard = possessionCard;
    }

    /** Runs this enemy's turn against the target and returns a log message describing it. */
    public String takeTurn(Entity target) {
        return behavior.act(this, target);
    }

    public String getIntent() {
        return behavior.describeIntent(this);
    }

    /** The card the hero gains by possessing this enemy's corpse. */
    public Card getPossessionCard() {
        return possessionCard;
    }
}
