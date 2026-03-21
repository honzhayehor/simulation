package units.concrete;

import enviroment.WorldMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import units.abstraction.Entity;
import units.configs.Energy;
import units.interfaces.Edible;
import units.interfaces.PlantBased;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GrassTest {

    private final WorldMap worldMap = Mockito.mock(WorldMap.class);


    @BeforeEach
    void resetIdCounter() throws Exception {
        Field idField = Entity.class.getDeclaredField("globalIdCounter");
        idField.setAccessible(true);
        ((AtomicInteger) idField.get(null)).set(0);
    }

    @Test
    void grassIdsAreIncremented() {
        Grass grass1 = Grass.create(worldMap);
        Grass grass2 = Grass.create(worldMap);
        assertEquals(1, grass1.getId());
        assertEquals(2, grass2.getId());
    }

    @Test
    void twoGrassInstancesAreNotEqual() {
        Grass grass1 = Grass.create(worldMap);
        Grass grass2 = Grass.create(worldMap);
        assertNotEquals(grass1, grass2);
    }

    @Test
    void returnsCorrectRepresentationWhenMethodCalled() {
        Grass grass = Grass.create(worldMap);
        assertEquals("🌿", grass.getRepresentation());
    }

    @Test
    void grassIsPassable() {
        Grass grass = Grass.create(worldMap);
        assertTrue(grass.isPassable());
    }

    @Test
    void grassReturnsCorrectEnergy() {
        Grass grass = Grass.create(worldMap);
        assertEquals(Energy.GRASS.getEnergy(), grass.getEnergy());
    }

    @Test
    void grassIsEntity() {
        Grass grass = Grass.create(worldMap);
        assertInstanceOf(Entity.class, grass);
    }

    @Test
    void grassMakeMoveDoesNothing() {
        Grass grass = Grass.create(worldMap);
        assertDoesNotThrow(grass::makeMove);
    }

    @Test
    void grassIsPlantBased() {
        Grass grass = Grass.create(worldMap);
        assertInstanceOf(PlantBased.class, grass);
    }
    @Test
    void grassIsEdible() {
        Grass grass = Grass.create(worldMap);
        assertInstanceOf(Edible.class, grass);
    }
}