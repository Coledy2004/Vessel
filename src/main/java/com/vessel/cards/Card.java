package com.vessel.cards;

import com.vessel.model.Entity;

/** A playable card. */
public interface Card {
    String getName();

    /** Energy needed to play this card. */
    int getCost();

    String getDescription();

    /** Applies the card's effect and returns a log message describing what happened. */
    String play(Entity user, Entity target);
}
