package com.vessel.cards;

import com.vessel.model.Entity;

public class BlockCard implements Card {
    private final String name;
    private final int cost;
    private final int block;

    public BlockCard(String name, int cost, int block) {
        this.name = name;
        this.cost = cost;
        this.block = block;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public String getDescription() {
        return "Gain " + block + " block.";
    }

    @Override
    public String play(Entity user, Entity target) {
        user.gainBlock(block);
        return user.getName() + " plays " + name + ": gains " + block + " block.";
    }
}
