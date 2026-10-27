package com.vessel.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Deck {
    private final List<Card> cards = new ArrayList<>();
    private final List<Card> drawPile = new ArrayList<>();
    private final List<Card> hand = new ArrayList<>();
    private final List<Card> discardPile = new ArrayList<>();
    private final Random random;

    public Deck(Random random) {
        this.random = random;
    }


    public static Deck starter(Random random) {
        Deck deck = new Deck(random);
        for (int i = 0; i < 5; i++) {
            deck.add(new AttackCard("Strike", 1, 6));
        }
        for (int i = 0; i < 4; i++) {
            deck.add(new BlockCard("Guard", 1, 5));
        }
        return deck;
    }

    public void add(Card card) {
        cards.add(card);
    }


    public void startCombat() {
        drawPile.clear();
        hand.clear();
        discardPile.clear();
        drawPile.addAll(cards);
        Collections.shuffle(drawPile, random);
    }

    public void draw(int count) {
        for (int i = 0; i < count; i++) {
            if (drawPile.isEmpty()) {
                if (discardPile.isEmpty()) {
                    return;
                }
                drawPile.addAll(discardPile);
                discardPile.clear();
                Collections.shuffle(drawPile, random);
            }
            hand.add(drawPile.remove(drawPile.size() - 1));
        }
    }

    public void discard(Card card) {
        if (hand.remove(card)) {
            discardPile.add(card);
        }
    }

    public void discardHand() {
        discardPile.addAll(hand);
        hand.clear();
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    public List<Card> getHand() {
        return Collections.unmodifiableList(hand);
    }

    public int getDrawPileSize() {
        return drawPile.size();
    }

    public int getDiscardPileSize() {
        return discardPile.size();
    }
}
