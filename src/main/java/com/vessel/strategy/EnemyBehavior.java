package com.vessel.strategy;

import com.vessel.model.Enemy;
import com.vessel.model.Entity;

/**
 * How an enemy decides what to do on its turn (Strategy pattern).
 * Swapping the behavior changes how an enemy fights without changing the Enemy class.
 */
public interface EnemyBehavior {
    /** Performs this turn's action and returns a log message describing it. */
    String act(Enemy self, Entity target);

    /** Short text telling the player what the enemy is about to do, e.g. "Attack for 8". */
    String describeIntent(Enemy self);
}
