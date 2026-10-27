package com.vessel.factory;

import com.vessel.cards.AttackCard;
import com.vessel.cards.BlockCard;
import com.vessel.model.Boss;
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
    public Boss createBoss() {
        // Hides behind bone wards, then goes all-out once badly hurt.
        return new Boss("Lich", 55, new DefensiveBehavior(7, 8), new AggressiveBehavior(13),
                new AttackCard("Soul Drain", 2, 18));
    }
}
