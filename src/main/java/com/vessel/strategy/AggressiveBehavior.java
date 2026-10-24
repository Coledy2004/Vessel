package com.vessel.strategy;

import com.vessel.model.Enemy;
import com.vessel.model.Entity;

/** Hits as hard as it can every turn. */
public class AggressiveBehavior implements EnemyBehavior {
    private final int damage;

    public AggressiveBehavior(int damage) {
        this.damage = damage;
    }

    @Override
    public String act(Enemy self, Entity target) {
        int taken = target.takeDamage(damage);
        return self.getName() + " attacks! " + target.getName() + " takes " + taken + " damage.";
    }

    @Override
    public String describeIntent(Enemy self) {
        return "Attack for " + damage;
    }
}
