package enviroment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import units.abstraction.Creature;
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
        worldMap.addEntityToCell(new Cell(1, 1), creature);
        assertTrue(worldMap.cellContainsEntity(1,1));
    }
}