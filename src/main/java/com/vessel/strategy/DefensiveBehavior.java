package com.vessel.strategy;

import com.vessel.model.Enemy;
import com.vessel.model.Entity;

/** Shields itself each turn and chips at the target with a weaker attack. */
public class DefensiveBehavior implements EnemyBehavior {
    private final int damage;
    private final int block;

    public DefensiveBehavior(int damage, int block) {
        this.damage = damage;
        this.block = block;
    }

    @Override
    public String act(Enemy self, Entity target) {
        self.gainBlock(block);
        int taken = target.takeDamage(damage);
        return self.getName() + " braces for " + block + " block and jabs " + target.getName()
                + " for " + taken + " damage.";
    }

    @Override
    public String describeIntent(Enemy self) {
        return "Block " + block + ", attack for " + damage;
    }
}
