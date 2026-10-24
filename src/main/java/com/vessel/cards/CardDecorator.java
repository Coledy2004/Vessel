package com.vessel.cards;

import com.vessel.model.Entity;

/**
 * Base class for cards that wrap another card and change its behavior (Decorator pattern).
 * By default every call is forwarded to the wrapped card; subclasses override what they change.
 * Decorators can be stacked: a card can be mutated more than once.
 */
public abstract class CardDecorator implements Card {
    protected final Card wrapped;

    protected CardDecorator(Card wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String getName() {
        return wrapped.getName();
    }

    @Override
    public int getCost() {
        return wrapped.getCost();
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription();
    }

    @Override
    public String play(Entity user, Entity target) {
        return wrapped.play(user, target);
    }
}
