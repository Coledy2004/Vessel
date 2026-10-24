package com.vessel.combat;

import com.vessel.cards.AttackCard;
import com.vessel.cards.Card;
import com.vessel.cards.Deck;
import com.vessel.model.Enemy;
import com.vessel.model.Hero;
import com.vessel.strategy.AggressiveBehavior;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CombatEncounterTest {
    private Deck strikesOnly() {
        Deck deck = new Deck(new Random(1));
        for (int i = 0; i < 5; i++) {
            deck.add(new AttackCard("Strike", 1, 6));
        }
        return deck;
    }

    @Test
    void playingCardSpendsEnergy() {
        Hero hero = new Hero("Hero", 50, strikesOnly());
        Enemy enemy = new Enemy("Rat", 30, new AggressiveBehavior(4), new AttackCard("Bite", 1, 5));
        CombatEncounter combat = new CombatEncounter(hero, enemy);
        combat.start();

        combat.playCard(hero.getDeck().getHand().get(0));

        assertEquals(Hero.ENERGY_PER_TURN - 1, combat.getEnergy());
        assertEquals(24, enemy.getHealth());
    }

    @Test
    void enemyAttacksAtEndOfTurn() {
        Hero hero = new Hero("Hero", 50, strikesOnly());
        Enemy enemy = new Enemy("Rat", 30, new AggressiveBehavior(4), new AttackCard("Bite", 1, 5));
        CombatEncounter combat = new CombatEncounter(hero, enemy);
        combat.start();

        combat.endTurn();

        assertEquals(46, hero.getHealth());
    }

    @Test
    void possessingAddsEnemyCardToDeck() {
        Hero hero = new Hero("Hero", 50, strikesOnly());
        Card bite = new AttackCard("Bite", 1, 5);
        Enemy enemy = new Enemy("Rat", 6, new AggressiveBehavior(4), bite);
        CombatEncounter combat = new CombatEncounter(hero, enemy);
        combat.start();
        combat.playCard(hero.getDeck().getHand().get(0));

        combat.possess();

        assertTrue(hero.getDeck().getCards().contains(bite));
    }
}
