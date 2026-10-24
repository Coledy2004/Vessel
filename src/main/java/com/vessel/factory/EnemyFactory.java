package com.vessel.factory;

import com.vessel.model.Enemy;

/**
 * Creates a matching family of enemies for one region of the dungeon (Abstract Factory pattern).
 * Each region's factory makes its own themed grunt, elite and boss, so the rest of the game
 * can ask for "a grunt" without knowing which region it is in.
 */
public interface EnemyFactory {
    String getRegionName();

    Enemy createGrunt();

    Enemy createElite();

    Enemy createBoss();
}
