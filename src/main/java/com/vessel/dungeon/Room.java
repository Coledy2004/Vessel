package com.vessel.dungeon;

import com.vessel.model.Enemy;

import java.util.List;

/** A single room: the leaf of the dungeon tree. It may hold an enemy, or be the exit. */
public class Room implements DungeonComponent {
    private final String name;
    private final Enemy enemy;
    private final boolean exit;

    private Room(String name, Enemy enemy, boolean exit) {
        this.name = name;
        this.enemy = enemy;
        this.exit = exit;
    }

    public static Room combat(String name, Enemy enemy) {
        return new Room(name, enemy, false);
    }

    public static Room exit(String name) {
        return new Room(name, null, true);
    }

    public boolean hasEnemy() {
        return enemy != null;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public boolean isExit() {
        return exit;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Room> getRooms() {
        return List.of(this);
    }
}
