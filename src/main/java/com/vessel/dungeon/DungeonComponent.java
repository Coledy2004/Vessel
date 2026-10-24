package com.vessel.dungeon;

import java.util.List;

/**
 * A piece of the dungeon: either a single {@link Room} (leaf) or a {@link Region}
 * that contains other pieces (Composite pattern). Code that walks the dungeon can treat
 * one room and a whole region the same way.
 */
public interface DungeonComponent {
    String getName();

    /** Every room inside this component, in the order the hero visits them. */
    List<Room> getRooms();

    default int getRoomCount() {
        return getRooms().size();
    }

    default long getEnemyCount() {
        return getRooms().stream().filter(Room::hasEnemy).count();
    }
}
