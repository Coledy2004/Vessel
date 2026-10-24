package com.vessel.cards;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeckTest {
    @Test
    void starterDeckHasNineCards() {
        assertEquals(9, Deck.starter(new Random(1)).getCards().size());
    }

    @Test
    void drawMovesCardsIntoHand() {
        Deck deck = Deck.starter(new Random(1));
        deck.startCombat();
        deck.draw(5);
        assertEquals(5, deck.getHand().size());
        assertEquals(4, deck.getDrawPileSize());
    }

    @Test
    void discardPileIsReshuffledWhenDrawPileRunsOut() {
        Deck deck = Deck.starter(new Random(1));
        deck.startCombat();
        deck.draw(5);
        deck.discardHand();
        deck.draw(5);
        assertEquals(5, deck.getHand().size());
        assertEquals(9, deck.getHand().size() + deck.getDrawPileSize() + deck.getDiscardPileSize());
    }

    @Test
    void replaceSwapsACard() {
        Deck deck = Deck.starter(new Random(1));
        Card old = deck.getCards().get(0);
        Card replacement = new AttackCard("Bone Rattle", 1, 8);
        deck.replace(old, replacement);
        assertEquals(replacement, deck.getCards().get(0));
        assertEquals(9, deck.getCards().size());
    }
}
