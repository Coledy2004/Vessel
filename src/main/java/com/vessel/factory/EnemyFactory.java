package com.vessel.factory;

import com.vessel.model.Boss;
import com.vessel.model.Enemy;

/**
 * Creates a matching family of enemies for one region of the dungeon (Abstract Factory pattern).
 * The family has two product types: regular {@link Enemy} objects (grunt and elite) and a
 * {@link Boss}, which changes behavior mid-fight. Each region's factory makes its own themed
 * set, so the rest of the game can ask for "a boss" without knowing which region it is in,
 * and a Crypt grunt is never paired with a Cavern boss.
 */
public interface EnemyFactory {
    String getRegionName();

    Enemy createGrunt();

    Enemy createElite();

    Boss createBoss();
}
