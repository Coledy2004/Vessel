package com.vessel.combat;

import com.vessel.cards.Card;
import com.vessel.model.Enemy;
import com.vessel.model.Hero;

/**
 * One turn-based fight between the hero and a single enemy. Contains only game rules,
 * no UI code, so it can be unit tested and driven by any front end.
 */
public class CombatEncounter {
    public static final int HAND_SIZE = 5;

    public enum Outcome { IN_PROGRESS, VICTORY, DEFEAT }

    private final Hero hero;
    private final Enemy enemy;
    private int energy;
    private boolean possessed;

    public CombatEncounter(Hero hero, Enemy enemy) {
        this.hero = hero;
        this.enemy = enemy;
    }

    /** Shuffles the deck and begins the hero's first turn. */
    public void start() {
        hero.getDeck().startCombat();
        startHeroTurn();
    }

    private void startHeroTurn() {
        hero.clearBlock();
        energy = Hero.ENERGY_PER_TURN;
        hero.getDeck().draw(HAND_SIZE);
    }

    public boolean canPlay(Card card) {
        return getOutcome() == Outcome.IN_PROGRESS
                && hero.getDeck().getHand().contains(card)
                && card.getCost() <= energy;
    }

    public String playCard(Card card) {
        if (!canPlay(card)) {
            throw new IllegalStateException("Cannot play " + card.getName() + " right now");
        }
        energy -= card.getCost();
        String log = card.play(hero, enemy);
        hero.getDeck().discard(card);
        return log;
    }

    /** Ends the hero's turn, lets the enemy act, then starts the hero's next turn. */
    public String endTurn() {
        if (getOutcome() != Outcome.IN_PROGRESS) {
            throw new IllegalStateException("Combat is already over");
        }
        hero.getDeck().discardHand();
        enemy.clearBlock();
        String log = enemy.takeTurn(hero);
        if (getOutcome() == Outcome.IN_PROGRESS) {
            startHeroTurn();
        }
        return log;
    }

    /** After winning, the hero possesses the corpse and adds the enemy's signature card to their deck. */
    public String possess() {
        if (getOutcome() != Outcome.VICTORY || possessed) {
            throw new IllegalStateException("There is nothing to possess");
        }
        possessed = true;
        hero.getDeck().add(enemy.getPossessionCard());
        return hero.getName() + " possesses the " + enemy.getName() + " and gains "
                + enemy.getPossessionCard().getName() + ".";
    }

    public Outcome getOutcome() {
        if (!hero.isAlive()) {
            return Outcome.DEFEAT;
        }
        if (!enemy.isAlive()) {
            return Outcome.VICTORY;
        }
        return Outcome.IN_PROGRESS;
    }

    public int getEnergy() {
        return energy;
    }

    public Hero getHero() {
        return hero;
    }

    public Enemy getEnemy() {
        return enemy;
    }
}
