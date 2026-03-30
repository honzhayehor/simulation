package units.concrete;

import enviroment.Cell;
import enviroment.WorldMap;
import logic.interfaces.Pathfinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import units.abstraction.Entity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ZebraTest {

    @Mock
    WorldMap map;

    @Mock
    Pathfinder pathfinder;

    @Mock
    Entity food;

    Zebra zebra;

    @BeforeEach
    void setUp() {
        zebra = Zebra.create(map, pathfinder);
    }

    @Test
    void shouldMoveTowardsFoodWhenFoodFoundAndNotAdjacent() {
        Cell current = new Cell(0, 0);
        Cell next = new Cell(1, 0);
        Cell target = new Cell(2, 0);

        when(map.getClosestEntity(zebra)).thenReturn(Optional.of(food));
        when(map.findCellOfEntity(zebra)).thenReturn(current);
        when(map.findCellOfEntity(food)).thenReturn(target);
        when(pathfinder.findPath(current, target))
                .thenReturn(List.of(current, next, target));

        zebra.performSingleStep();

        verify(map).moveEntity(zebra, next);
    }
}