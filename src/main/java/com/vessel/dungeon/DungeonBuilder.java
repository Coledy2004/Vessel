package com.vessel.dungeon;

import com.vessel.factory.CavernEnemyFactory;
import com.vessel.factory.CryptEnemyFactory;
import com.vessel.factory.EnemyFactory;

/**
 * Builds the dungeon. For now this is a fixed, linear layout; later it should generate
 * branching paths the player chooses between.
 */
public final class DungeonBuilder {
    private DungeonBuilder() {
    }

    public static Region buildDefault() {
        Region crypt = buildRegion(new CryptEnemyFactory());
        Region caverns = buildRegion(new CavernEnemyFactory())
                .add(Room.exit("The Surface"));
        return new Region("The Dungeon").add(crypt).add(caverns);
    }

    /** Builds one region whose enemies all come from the given factory. */
    public static Region buildRegion(EnemyFactory factory) {
        String region = factory.getRegionName();
        return new Region(region)
                .add(Room.combat(region + " - Entrance", factory.createGrunt()))
                .add(Room.combat(region + " - Hall", factory.createGrunt()))
                .add(Room.combat(region + " - Sanctum", factory.createElite()))
                .add(Room.combat(region + " - Throne", factory.createBoss()));
    }
}
