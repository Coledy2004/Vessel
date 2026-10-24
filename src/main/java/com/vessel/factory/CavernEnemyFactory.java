package com.vessel.factory;

import com.vessel.cards.AttackCard;
import com.vessel.cards.BlockCard;
import com.vessel.model.Enemy;
import com.vessel.strategy.AggressiveBehavior;
import com.vessel.strategy.DefensiveBehavior;

/** Beasts for the deeper cavern region of the dungeon. */
public class CavernEnemyFactory implements EnemyFactory {
    @Override
    public String getRegionName() {
        return "The Caverns";
    }

    @Override
    public Enemy createGrunt() {
        return new Enemy("Cave Spider", 24, new AggressiveBehavior(7),
                new AttackCard("Venom Fang", 1, 9), 6);
    }

    @Override
    public Enemy createElite() {
        return new Enemy("Stone Troll", 45, new DefensiveBehavior(8, 8),
                new BlockCard("Stone Hide", 1, 11), 12);
    }

    @Override
    public Enemy createBoss() {
        return new Enemy("Wyrm", 70, new AggressiveBehavior(14),
                new AttackCard("Wyrmfire", 2, 22), 30);
    }
}
