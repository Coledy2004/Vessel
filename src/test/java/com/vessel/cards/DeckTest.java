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
}
