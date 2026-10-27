package com.vessel.cards;

import com.vessel.model.Entity;

public interface Card {
    String getName();

    int getCost();

    String getDescription();

    String play(Entity user, Entity target);
}
