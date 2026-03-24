package enviroment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import units.abstraction.Creature;
import units.abstraction.Entity;
import units.concrete.Grass;
import units.concrete.Rock;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorldMapTest {
    WorldMap worldMap;

    @BeforeEach
    void initWorldMap() {
        worldMap = new WorldMap();
    }


    @Test
    void givenEmptyMapContainsEntityReturnsFalse() {
        WorldMap wp = new WorldMap(10, 10);
        assertFalse(wp.cellContainsEntity(1, 1));
    }
    @Test
    void givenNonEmptyMapContainsEntityReturnsTrue() {
        Creature creature = Mockito.mock(Creature.class);
        worldMap.addEntityToCell(worldMap.cellOf(1, 1).get(), creature);
        assertTrue(worldMap.cellContainsEntity(1,1));
    }

    @Test
    void whenNoSizeProvidedIfBoundToDefault() {
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
        Map<Cell, Set<Entity>> map = worldMap.getMap();
        Cell anyCell = map.keySet().iterator().next();

        assertThrows(UnsupportedOperationException.class,
                () -> map.remove(anyCell));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void givenUnmodifiableMapThrowsOnPut() {
        Map<Cell, Set<Entity>> map = worldMap.getMap();

        Cell cell = new Cell(0,0);
        Set<Entity> s = new HashSet<>();

        assertThrows(UnsupportedOperationException.class, () -> map.put(cell, s));
    }

    @Test
    void givenUnmodifiableMapThrowsOnClear() {
        Map<Cell, Set<Entity>> map = worldMap.getMap();

        assertThrows(UnsupportedOperationException.class,
                map::clear);
    }

    @Test
    void givenNotPopulatedMapEmptyListReturnedOnAllEntitiesCall() {
        List<Entity> entityList = worldMap.getAllEntities();
        assertEquals(0, entityList.size());
    }
    @Test
    void givenPopulatedMapEmptyListReturnedOnAllEntitiesCall() {
        Cell cell1 = worldMap.cellOf(1, 1).orElseThrow(() -> new IllegalArgumentException("Cell is not found"));
        Cell cell2 = worldMap.cellOf(2, 2).orElseThrow(() -> new IllegalArgumentException("Cell is not found"));
        Entity entity1 = Mockito.mock(Entity.class);
        Entity entity2 = Mockito.mock(Entity.class);
        worldMap.addEntityToCell(cell1, entity1);
        worldMap.addEntityToCell(cell2, entity2);
        List<Entity> entityList = worldMap.getAllEntities();
        assertEquals(2, entityList.size());
    }

    @Test
    void containsEntityThrowsErrorWhenNegativeCoordinatesPassed() {
        assertThrows(IllegalArgumentException.class, () -> worldMap.cellContainsEntity(-1, -1));
    }

    @Test
    void containsEntityReturnsFalseWhenCellIsEmptyByCoordinate() {
        assertFalse(worldMap.cellContainsEntity(1, 1));
    }

    @Test
    void containsEntityReturnsFalseWhenCellIsEmptyByCell() {
        assertFalse(worldMap.cellContainsEntity(worldMap.cellOf(1, 1).orElseThrow(() -> new IllegalArgumentException("Cell does not exist"))));
    }


    @Test
    void containsEntityThrowsWhenCellIsNull() {
        assertThrows(IllegalArgumentException.class, () -> worldMap.cellContainsEntity(null));
    }
    @Test
    void givenNullCellAddEntityToCellThrowsError() {
        assertThrows(IllegalArgumentException.class, () -> worldMap.addEntityToCell(null, Mockito.mock(Entity.class)));
    }
    @Test
    @SuppressWarnings("java:S5778")
    void givenNullEntityAddEntityToCellThrowsError() {
        assertThrows(IllegalArgumentException.class, () -> worldMap.addEntityToCell(worldMap.cellOf(1, 1).get(), null));
    }

    @Test
    @SuppressWarnings("java:S5778")
    void givenNonNullEntityAndCellAddEntityToCellWorksFine() {
        assertDoesNotThrow(() -> worldMap.addEntityToCell(worldMap.cellOf(1,1).get(), Mockito.mock(Entity.class)));
    }

    @Test
    @SuppressWarnings("java:S5778")
    void givenCellWithNoPassableEntitiesFalseReturns() {
        worldMap.addEntityToCell(worldMap.cellOf(1, 1).get(), Mockito.mock(Rock.class));
        assertFalse(worldMap.suggestMove(worldMap.cellOf(1, 1).get()));
    }

    @Test
    @SuppressWarnings("java:S5778")
    void givenNullWithNoPassableEntitiesErrorThrown() {
        worldMap.addEntityToCell(worldMap.cellOf(1, 1).get(), Mockito.mock(Rock.class));
        assertThrows(IllegalArgumentException.class, () -> worldMap.suggestMove(null));
    }

    @Test
    @SuppressWarnings("java:S5778")
    void givenCellWithAllPassableEntitiesTrueReturns() {
        Grass grass = Mockito.mock(Grass.class);
        when(grass.isPassable()).thenReturn(true);
        worldMap.addEntityToCell(worldMap.cellOf(1, 1).get(), grass);
        assertTrue(worldMap.suggestMove(worldMap.cellOf(1, 1).get()));
    }

    @Test
    @SuppressWarnings("java:S5778")
    void givenCellWithNoEntitiesTrueReturns() {
        assertTrue(worldMap.suggestMove(worldMap.cellOf(1, 1).get()));
    }

    @Test
    void entityMovesToDestination() {
        Grass grass = Grass.create(worldMap);
        Cell source = worldMap.cellOf(1, 1).orElseThrow();
        Cell destination = worldMap.cellOf(1, 2).orElseThrow();
        worldMap.addEntityToCell(source, grass);

        worldMap.moveEntity(grass, destination);

        assertTrue(worldMap.getMap().get(destination).contains(grass));
    }

    @Test
    void entityRemovedFromSource() {
        Grass grass = Grass.create(worldMap);
        Cell source = worldMap.cellOf(1, 1).orElseThrow();
        Cell destination = worldMap.cellOf(1, 2).orElseThrow();
        worldMap.addEntityToCell(source, grass);

        worldMap.moveEntity(grass, destination);

        assertFalse(worldMap.getMap().get(source).contains(grass));
    }

    @Test
    void entityLocationUpdatedAfterMove() {
        Grass grass = Grass.create(worldMap);
        Cell source = worldMap.cellOf(1, 1).orElseThrow();
        Cell destination = worldMap.cellOf(1, 2).orElseThrow();
        worldMap.addEntityToCell(source, grass);

        worldMap.moveEntity(grass, destination);

        assertEquals(destination, worldMap.findCellOfEntity(grass));
    }

    @Test
    void entityDoesNotMoveWhenDestinationNotPassable() {
        Grass grass = Grass.create(worldMap);
        Rock rock = Rock.create(worldMap);
        Cell source = worldMap.cellOf(1, 1).orElseThrow();
        Cell destination = worldMap.cellOf(1, 2).orElseThrow();
        worldMap.addEntityToCell(source, grass);
        worldMap.addEntityToCell(destination, rock);

        worldMap.moveEntity(grass, destination);

        assertEquals(source, worldMap.findCellOfEntity(grass));
        assertTrue(worldMap.getMap().get(source).contains(grass));
    }

    @Test
    void givenNullEntityThrowsError() {
        Grass grass = null;
        Cell cell = worldMap.cellOf(1,1).get();
        assertThrows(NullPointerException.class, () -> worldMap.removeEntity(grass, cell));
    }

    @Test
    void givenNullCellThrowsError() {
        Grass grass = Mockito.mock(Grass.class);
        Cell cell = null;
        assertThrows(NullPointerException.class, () -> worldMap.removeEntity(grass, cell));
    }

    @Test
    void throwsErrorWhenEntityIsNotInCellAndTryToRemove() {
        Grass grass = Grass.create(worldMap);
        Cell cell = worldMap.cellOf(1,1).get();
        assertThrows(IllegalArgumentException.class, () -> worldMap.removeEntity(grass, cell));
    }

    @Test
    void successfullyRemoveEntity() {
        Grass grass = Grass.create(worldMap);
        Cell cell = worldMap.cellOf(1,1).get();
        worldMap.addEntityToCell(cell, grass);
        assertDoesNotThrow(() -> worldMap.removeEntity(grass, cell));
    }

    @Test
    void givenBadCellReturnsFalse() {
        worldMap = new WorldMap(10, 10);
        Cell cell = new Cell(14, 14);
        assertFalse(worldMap.isValidCell(cell));
    }

    @Test
    void givenGoodCellReturnsTrue() {
        worldMap = new WorldMap(10, 10);
        Cell cell = new Cell(2, 1);
        assertTrue(worldMap.isValidCell(cell));
    }

    @Test
    void returnsUnmodifiableListOfCells() {
        List<Cell> cells = worldMap.asList();
        assertThrows(UnsupportedOperationException.class, () -> cells.remove(1));
    }

    @Test
    void returnsCorrectCountOfCells() {
        int width = 5;
        int height = 5;
        int expected = width * height;
        worldMap = new WorldMap(width, height);
        List<Cell> cells = worldMap.asList();
        int real = cells.size();
        assertEquals(expected, real);
    }
}