package com.vessel.model;

import com.vessel.cards.AttackCard;
import com.vessel.cards.Deck;
import com.vessel.factory.CryptEnemyFactory;
import com.vessel.strategy.AggressiveBehavior;
import com.vessel.strategy.DefensiveBehavior;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BossTest {
    private final Hero hero = new Hero("Hero", 50, new Deck(new Random(1)));

    private Boss ogre() {
        return new Boss("Ogre", 20, new DefensiveBehavior(2, 5), new AggressiveBehavior(9),
                new AttackCard("Club", 1, 7));
    }

    @Test
    void bossUsesNormalBehaviorAboveHalfHealth() {
        Boss boss = ogre();
        boss.takeTurn(hero);
        assertFalse(boss.isEnraged());
        assertEquals(48, hero.getHealth());
        assertEquals(5, boss.getBlock());
    }

    @Test
    void bossSwitchesBehaviorAtHalfHealth() {
        Boss boss = ogre();
        boss.takeDamage(10);
        boss.takeTurn(hero);
        assertTrue(boss.isEnraged());
        assertEquals(41, hero.getHealth());
        assertEquals(0, boss.getBlock());
        assertTrue(boss.getIntent().startsWith("ENRAGED!"));
    }

    @Test
    void factoryBossChangesIntentWhenHurt() {
        Boss lich = new CryptEnemyFactory().createBoss();
        String before = lich.getIntent();
        lich.takeDamage(lich.getMaxHealth() / 2 + 1);
        assertNotEquals(before, lich.getIntent());
    }
}
