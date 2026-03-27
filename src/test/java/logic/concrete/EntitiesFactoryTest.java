package logic.concrete;

import enviroment.WorldMap;
import logic.config.CreationConfig;
import logic.interfaces.Pathfinder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import units.abstraction.Entity;
import units.concrete.Grass;
import units.concrete.Zebra;

import static org.junit.jupiter.api.Assertions.*;

class EntitiesFactoryTest {

    @Test
    void givenZebraReturnsZebraInstance() {
        WorldMap worldMap = Mockito.mock(WorldMap.class);
        Pathfinder pathfinder = Mockito.mock(Pathfinder.class);

        Entity entity = EntitiesFactory.getEntity(CreationConfig.ZEBRA, worldMap, pathfinder).orElseThrow(() -> new IllegalArgumentException("Cannot create such object"));
        assertInstanceOf(Zebra.class, entity);
    }

    @Test
    void givenNullThrowsError() {
        WorldMap worldMap = Mockito.mock(WorldMap.class);
        Pathfinder pathfinder = Mockito.mock(Pathfinder.class);
        assertThrows(IllegalArgumentException.class, () -> EntitiesFactory.getEntity(null, worldMap, pathfinder));
    }

    @Test
    void givenEntityThatDoesNotRequirePathfinderReturnsCorrectEntity() {
        WorldMap worldMap = Mockito.mock(WorldMap.class);
        Pathfinder pathfinder = Mockito.mock(Pathfinder.class);
        Entity entity = EntitiesFactory.getEntity(CreationConfig.GRASS, worldMap, pathfinder).orElseThrow(() -> new IllegalArgumentException("Cannot create such object"));
        assertInstanceOf(Grass.class, entity);
    }

}