package com.vessel.cards;

import com.vessel.model.Entity;

/**
 * A card twisted by the hero's mutation: it still does everything the original card does,
 * plus extra damage to the target.
 */
public class MutatedCard extends CardDecorator {
    private final int bonusDamage;

    public MutatedCard(Card wrapped, int bonusDamage) {
        super(wrapped);
        this.bonusDamage = bonusDamage;
    }

    @Override
    public String getName() {
        return "Mutated " + wrapped.getName();
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " Deal " + bonusDamage + " extra damage.";
    }

    @Override
    public String play(Entity user, Entity target) {
        String base = wrapped.play(user, target);
        int taken = target.takeDamage(bonusDamage);
        return base + " The mutation lashes out for " + taken + " more.";
    }
}
