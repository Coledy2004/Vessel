package com.vessel.cards;

import com.vessel.model.Enemy;
import com.vessel.model.Hero;
import com.vessel.strategy.AggressiveBehavior;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardTest {
    private Hero hero;
    private Enemy dummy;

    @BeforeEach
    void setUp() {
        hero = new Hero("Hero", 50, new Deck(new Random(1)));
        dummy = new Enemy("Dummy", 30, new AggressiveBehavior(0), new AttackCard("Poke", 0, 1));
    }

    @Test
    void attackDealsDamage() {
        new AttackCard("Strike", 1, 6).play(hero, dummy);
        assertEquals(24, dummy.getHealth());
    }

    @Test
    void blockAbsorbsDamage() {
        new BlockCard("Guard", 1, 5).play(hero, dummy);
        hero.takeDamage(8);
        assertEquals(47, hero.getHealth());
    }
}
