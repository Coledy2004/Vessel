package com.vessel.dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** A named area made of rooms and/or smaller regions: the composite node of the dungeon tree. */
public class Region implements DungeonComponent {
    private final String name;
    private final List<DungeonComponent> children = new ArrayList<>();

    public Region(String name) {
        this.name = name;
    }

    public Region add(DungeonComponent child) {
        children.add(child);
        return this;
    }

    public List<DungeonComponent> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Room> getRooms() {
        List<Room> rooms = new ArrayList<>();
        for (DungeonComponent child : children) {
            rooms.addAll(child.getRooms());
        }
        return rooms;
    }
}
