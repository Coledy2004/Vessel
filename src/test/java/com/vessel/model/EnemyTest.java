package com.vessel.model;

import com.vessel.cards.AttackCard;
import com.vessel.cards.Deck;
import com.vessel.factory.CavernEnemyFactory;
import com.vessel.factory.CryptEnemyFactory;
import com.vessel.strategy.AggressiveBehavior;
import com.vessel.strategy.DefensiveBehavior;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnemyTest {
    private final Hero hero = new Hero("Hero", 50, new Deck(new Random(1)));

    @Test
    void aggressiveEnemyAttacks() {
        Enemy enemy = new Enemy("Rat", 10, new AggressiveBehavior(5), new AttackCard("Bite", 1, 5));
        enemy.takeTurn(hero);
        assertEquals(45, hero.getHealth());
    }

    @Test
    void defensiveEnemyBlocksAndAttacks() {
        Enemy enemy = new Enemy("Turtle", 10, new DefensiveBehavior(2, 4), new AttackCard("Snap", 1, 3));
        enemy.takeTurn(hero);
        assertEquals(48, hero.getHealth());
        assertEquals(4, enemy.getBlock());
    }

    @Test
    void factoriesMakeTheirOwnEnemies() {
        assertEquals("Skeleton", new CryptEnemyFactory().createGrunt().getName());
        assertEquals("Cave Spider", new CavernEnemyFactory().createGrunt().getName());
    }
}
