package enviroment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import units.abstraction.Creature;
import units.abstraction.Entity;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WorldMapTest {
    @Test
    void givenEmptyMapContainsEntityReturnsFalse() {
        WorldMap worldMap = new WorldMap(10, 10);
        assertFalse(worldMap.cellContainsEntity(1, 1));
    }
    @Test
    void givenNonEmptyMapContainsEntityReturnsTrue() {
        WorldMap worldMap = new WorldMap(10, 10);
        Creature creature = Mockito.mock(Creature.class);
        worldMap.addEntityToCell(worldMap.cellOf(1, 1).get(), creature);
        assertTrue(worldMap.cellContainsEntity(1,1));
    }

    @Test
    void whenNoSizeProvidedIfBoundToDefault() {
        WorldMap worldMap = new WorldMap();
        int width = worldMap.getWidth();
        int height = worldMap.getHeight();
        int expectedWidth = 10;
        int expectedHeight = 10;
        assertEquals(expectedWidth, width);
        assertEquals(expectedHeight, height);
    }

    @Test
    void givenNegativeXAndYExceptionExpected() {
        assertThrows(IllegalArgumentException.class, () -> new WorldMap(-1, -1));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void givenUnmodifiableMapThrowsOnRemove() {
        WorldMap worldMap = new WorldMap();
        Map<Cell, Set<Entity>> map = worldMap.getMap();
        Cell anyCell = map.keySet().iterator().next();

        assertThrows(UnsupportedOperationException.class,
                () -> map.remove(anyCell));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void givenUnmodifiableMapThrowsOnPut() {
        WorldMap worldMap = new WorldMap();
        Map<Cell, Set<Entity>> map = worldMap.getMap();

        Cell cell = new Cell(0,0);
        Set<Entity> s = new HashSet<>();

        assertThrows(UnsupportedOperationException.class, () -> map.put(cell, s));
    }

    @Test
    void givenUnmodifiableMapThrowsOnClear() {
        WorldMap worldMap = new WorldMap();
        Map<Cell, Set<Entity>> map = worldMap.getMap();

        assertThrows(UnsupportedOperationException.class,
                map::clear);
    }

    @Test
    void givenNotPopulatedMapEmptyListReturnedOnAllEntitiesCall() {
        WorldMap worldMap = new WorldMap();
        List<Entity> entityList = worldMap.getAllEntities();
        assertEquals(0, entityList.size());
    }
    @Test
    void givenPopulatedMapEmptyListReturnedOnAllEntitiesCall() {
        WorldMap worldMap = new WorldMap();
        Cell cell1 = worldMap.cellOf(1, 1).orElseThrow(() -> new IllegalArgumentException("Cell is not found"));
        Cell cell2 = worldMap.cellOf(2, 2).orElseThrow(() -> new IllegalArgumentException("Cell is not found"));
        Entity entity1 = Mockito.mock(Entity.class);
        Entity entity2 = Mockito.mock(Entity.class);
        worldMap.addEntityToCell(cell1, entity1);
        worldMap.addEntityToCell(cell2, entity2);
        List<Entity> entityList = worldMap.getAllEntities();
        assertEquals(2, entityList.size());
    }

}