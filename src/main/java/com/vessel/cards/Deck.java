package com.vessel.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * The hero's cards. Owns the full card list plus the draw pile, hand and discard pile
 * used during combat.
 */
public class Deck {
    private final List<Card> cards = new ArrayList<>();
    private final List<Card> drawPile = new ArrayList<>();
    private final List<Card> hand = new ArrayList<>();
    private final List<Card> discardPile = new ArrayList<>();
    private final Random random;

    public Deck(Random random) {
        this.random = random;
    }

    /** The standard deck the hero starts a run with. */
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

    /** Swaps one card in the deck for another (e.g. replacing a starter card with a possessed one). */
    public void replace(Card oldCard, Card newCard) {
        int index = cards.indexOf(oldCard);
        if (index < 0) {
            throw new IllegalArgumentException(oldCard.getName() + " is not in the deck");
        }
        cards.set(index, newCard);
    }

    /** Resets the piles at the start of a fight: everything shuffled into the draw pile. */
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

    /** Moves a card from the hand to the discard pile after it is played. */
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
