package units.concrete;

import enviroment.Cell;
import enviroment.WorldMap;
import logic.concrete.EntitiesFactory;
import logic.config.CreationConfig;
import logic.interfaces.Pathfinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import units.abstraction.Creature;
import units.abstraction.Entity;
import units.interfaces.Edible;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WolfTest {

    @Mock
    WorldMap map;

    @Mock
    Pathfinder pathfinder;

    @Mock
    Entity food;

    Wolf wolf;

    @BeforeEach
    void setUp() {
        wolf = Wolf.create(map, pathfinder);
    }

    @Test
    void shouldMoveTowardsFoodWhenFoodFoundAndNotAdjacent() {
        Cell current = new Cell(0, 0);
        Cell next = new Cell(1, 0);
        Cell target = new Cell(2, 0);

        when(map.getClosestEntity(wolf)).thenReturn(Optional.of(food));
        when(map.findCellOfEntity(wolf)).thenReturn(current);
        when(map.findCellOfEntity(food)).thenReturn(target);
        when(pathfinder.findPath(current, target))
                .thenReturn(List.of(current, next, target));

        wolf.performSingleStep();

        verify(map).moveEntity(wolf, next);
    }


    @Test
    void shouldNotMoveWhenPathTooShort() {
        Cell current = new Cell(0, 0);

        when(map.getClosestEntity(wolf)).thenReturn(Optional.of(food));
        when(map.findCellOfEntity(wolf)).thenReturn(current);
        when(map.findCellOfEntity(food)).thenReturn(current);
        when(pathfinder.findPath(current, current))
                .thenReturn(List.of(current));

        wolf.performSingleStep();

        verify(map, never()).moveEntity(any(), any());
    }

    @Test
    void shouldWanderWhenNoFoodFound() {
        Cell current = new Cell(0, 0);
        Cell neighbor = new Cell(1, 0);

        when(map.getClosestEntity(wolf)).thenReturn(Optional.empty());
        when(map.findCellOfEntity(wolf)).thenReturn(current);

        when(map.cellOf(anyInt(), anyInt()))
                .thenReturn(Optional.of(neighbor));

        when(map.isValidCell(neighbor)).thenReturn(true);
        when(map.suggestMove(neighbor)).thenReturn(true);

        wolf.performSingleStep();

        verify(map).moveEntity(eq(wolf), any());
    }

    @Test
    void whenFoodCloseEatInsteadOfMoving() {
        Cell current = new Cell(0, 0);
        Cell target = new Cell(1,0);
        Zebra zebra = (Zebra) EntitiesFactory.getEntity(CreationConfig.ZEBRA, Mockito.mock(WorldMap.class), Mockito.mock(Pathfinder.class)).get();

        when(map.getClosestEntity(wolf)).thenReturn(Optional.of(zebra));
        when(map.findCellOfEntity(wolf)).thenReturn(current);
        when(map.findCellOfEntity(zebra)).thenReturn(target);
        when(pathfinder.findPath(current, target))
                .thenReturn(List.of(current, target));

        int zebraHpBefore = zebra.getHp();
        wolf.performSingleStep();
        int zebraHpAfter = zebra.getHp();

        assertTrue(zebraHpBefore > zebraHpAfter);
    }


    @Test
    void whenFoodCloseEatInsteadOfMovingAndVerifyFoodIsDead() {
        Cell current = new Cell(0, 0);
        Cell target = new Cell(1,0);
        Zebra zebra = (Zebra) EntitiesFactory.getEntity(CreationConfig.ZEBRA, mock(WorldMap.class), mock(Pathfinder.class)).get();

        when(map.getClosestEntity(wolf)).thenReturn(Optional.of(zebra));
        when(map.findCellOfEntity(wolf)).thenReturn(current);
        when(map.findCellOfEntity(zebra)).thenReturn(target);
        when(pathfinder.findPath(current, target))
                .thenReturn(List.of(current, target));

        wolf.performSingleStep();
        wolf.performSingleStep();

        assertTrue(zebra.getHp() <= 0);
    }

}