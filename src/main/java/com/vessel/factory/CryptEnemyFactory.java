package com.vessel.factory;

import com.vessel.cards.AttackCard;
import com.vessel.cards.BlockCard;
import com.vessel.model.Enemy;
import com.vessel.strategy.AggressiveBehavior;
import com.vessel.strategy.DefensiveBehavior;

/** Undead enemies for the first region of the dungeon. */
public class CryptEnemyFactory implements EnemyFactory {
    @Override
    public String getRegionName() {
        return "The Crypt";
    }

    @Override
    public Enemy createGrunt() {
        return new Enemy("Skeleton", 20, new AggressiveBehavior(5),
                new AttackCard("Bone Rattle", 1, 8));
    }

    @Override
    public Enemy createElite() {
        return new Enemy("Grave Knight", 34, new DefensiveBehavior(6, 6),
                new BlockCard("Rusted Bulwark", 1, 9));
    }

    @Override
    public Enemy createBoss() {
        return new Enemy("Lich", 55, new AggressiveBehavior(11),
                new AttackCard("Soul Drain", 2, 18));
    }
}
