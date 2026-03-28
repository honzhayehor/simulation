package logic.concrete;

import enviroment.Cell;
import enviroment.WorldMap;
import logic.interfaces.Pathfinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AStarTest {


    private WorldMap worldMap;
    private Pathfinder algorithm;

    @BeforeEach
    void init() {
        worldMap = new WorldMap(5, 5);
        algorithm = new AStar(worldMap);
    }

    @Test
    void creatingClassWithNullInConstructorRaisesError() {
        assertThrows(NullPointerException.class, () -> new AStar(null) );
    }

    @Test
    void givenValidCoordinatesReturnsCorrectPath() {
        Cell currentPosition = worldMap.cellOf(1, 1).get();
        Cell targetPosition = worldMap.cellOf(2, 2).get();
        List<Cell> path = algorithm.findPath(currentPosition, targetPosition);
        assertFalse(path.isEmpty());
    }

    @Test
    void givenNullCellThrowsException() {
        Cell currentPosition = null;
        Cell targetPosition = worldMap.cellOf(2, 2).get();
        assertThrows(NullPointerException.class, () -> algorithm.findPath(currentPosition, targetPosition));
    }

    @Test
    void givenNormalCoordinatesReturnsPathWithAllNeededCells() {
        Cell currentPosition = worldMap.cellOf(1, 1).get();
        Cell targetPosition = worldMap.cellOf(2, 2).get();
        List<Cell> path = algorithm.findPath(currentPosition, targetPosition);
        List<Cell> expected = List.of(
                worldMap.cellOf(1, 1).get(),
                worldMap.cellOf(1, 2).get(),
                worldMap.cellOf(2, 1).get(),
                worldMap.cellOf(2, 2).get()
        );
        assertTrue(expected.containsAll(path));
    }

    @Test
    void givenIdenticalCurrentCellAndTargetReturnsListWithOneElement() {
        Cell currentPosition = worldMap.cellOf(1, 1).get();
        Cell targetPosition = worldMap.cellOf(1, 1).get();
        List<Cell> path = algorithm.findPath(currentPosition, targetPosition);
        List<Cell> expected = List.of(
                worldMap.cellOf(1, 1).get()
        );
        assertTrue(expected.containsAll(path));
    }

    @Test
    void providedPathWhenModifiedThrowsError() {
        Cell currentPosition = worldMap.cellOf(1, 1).get();
        Cell targetPosition = worldMap.cellOf(1, 1).get();
        List<Cell> path = algorithm.findPath(currentPosition, targetPosition);
        assertThrows(UnsupportedOperationException.class, () -> path.remove(1));
    }

    @Test
    void givenCurrentCellAndTargetAreOneCellAwayListContainsTwoElements() {
        Cell currentPosition = worldMap.cellOf(1, 1).get();
        Cell targetPosition = worldMap.cellOf(1, 2).get();
        List<Cell> path = algorithm.findPath(currentPosition, targetPosition);
        List<Cell> expected = List.of(
                worldMap.cellOf(1, 1).get(),
                worldMap.cellOf(1, 2).get()
        );
        assertTrue(expected.containsAll(path));
    }

    @Test
    void cellCountStartFromZeroZero() {
        Cell firstCell = worldMap.asList().get(0);
        assertEquals(worldMap.cellOf(0,0).get(), firstCell);
    }

}