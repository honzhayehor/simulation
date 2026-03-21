package units.concrete;

import enviroment.WorldMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import units.abstraction.Entity;
import units.interfaces.Edible;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class RockTest {

    private final WorldMap worldMap = Mockito.mock(WorldMap.class);


    @BeforeEach
    void resetIdCounter() throws Exception {
        Field idField = Entity.class.getDeclaredField("globalIdCounter");
        idField.setAccessible(true);
        ((AtomicInteger) idField.get(null)).set(0);
    }

    @Test
    void rockIdsAreIncremented() {
        Rock rock1 = Rock.create(worldMap);
        Rock rock2 = Rock.create(worldMap);
        assertEquals(1, rock1.getId());
        assertEquals(2, rock2.getId());
    }

    @Test
    void twoRockInstancesAreNotEqual() {
        Rock rock1 = Rock.create(worldMap);
        Rock rock2 = Rock.create(worldMap);
        assertNotEquals(rock1, rock2);
    }

    @Test
    void returnsCorrectRepresentationWhenMethodCalled() {
        Rock rock = Rock.create(worldMap);
        assertEquals("🪨", rock.getRepresentation());
    }

    @Test
    void rockIsPassable() {
        Rock rock = Rock.create(worldMap);
        assertFalse(rock.isPassable());
    }


    @Test
    void rockIsEntity() {
        Rock rock = Rock.create(worldMap);
        assertInstanceOf(Entity.class, rock);
    }

    @Test
    void rockMakeMoveDoesNothing() {
        Rock rock = Rock.create(worldMap);
        assertDoesNotThrow(rock::makeMove);
    }

    @Test
    void rockIsNotEdible() {
        Rock rock = Rock.create(worldMap);
        assertFalse(rock instanceof Edible);
    }
}