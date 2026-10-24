package com.vessel.dungeon;

import com.vessel.factory.CavernEnemyFactory;
import com.vessel.factory.CryptEnemyFactory;
import com.vessel.factory.EnemyFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DungeonTest {
    @Test
    void regionCollectsRoomsFromNestedRegions() {
        Region dungeon = DungeonBuilder.buildDefault();
        List<Room> rooms = dungeon.getRooms();

        assertEquals(9, dungeon.getRoomCount());
        assertEquals(8, dungeon.getEnemyCount());
        assertTrue(rooms.get(rooms.size() - 1).isExit());
    }

    @Test
    void singleRoomIsItsOwnComponent() {
        Room exit = Room.exit("Door");
        assertEquals(List.of(exit), exit.getRooms());
        assertEquals(0, exit.getEnemyCount());
    }

    @Test
    void factoriesProduceRegionThemedEnemies() {
        EnemyFactory crypt = new CryptEnemyFactory();
        EnemyFactory caverns = new CavernEnemyFactory();

        assertEquals("Skeleton", crypt.createGrunt().getName());
        assertEquals("Cave Spider", caverns.createGrunt().getName());
        assertNotSame(crypt.createGrunt(), crypt.createGrunt());
    }
}
