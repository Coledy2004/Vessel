package com.vessel.cards;

import com.vessel.model.Entity;

/** Base for mutations: wraps a card and forwards everything to it by default (Decorator pattern). */
/**Will be used to add secondary effects to more advanced cards in the later stages of the game*/
public abstract class DecoratedCard implements Card {
    protected final Card inner;

    protected DecoratedCard(Card inner) {
        this.inner = inner;
    }

    @Override public String getName()        { return inner.getName(); }
    @Override public int getCost()           { return inner.getCost(); }
    @Override public String getDescription() { return inner.getDescription(); }
    @Override public String play(Entity user, Entity target) { return inner.play(user, target); }
}