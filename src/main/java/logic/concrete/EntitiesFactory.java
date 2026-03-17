package logic.concrete;

import enviroment.WorldMap;
import logic.config.CreationConfig;
import logic.interfaces.Pathfinder;
import units.abstraction.Entity;
import units.concrete.*;

import java.util.Optional;

public class EntitiesFactory {
    private EntitiesFactory() {}

    public static Optional<Entity> getEntity(CreationConfig config, WorldMap worldMap, Pathfinder pathfinder) {
        return switch (config) {
            case ZEBRA -> Optional.of(Zebra.create(worldMap, pathfinder));
            case GRASS -> Optional.of(Grass.create(worldMap));
            case TREE -> Optional.of(Tree.create(worldMap));
            case WOLF -> Optional.of(Wolf.create(worldMap, pathfinder));
            case ROCK -> Optional.of(Rock.create(worldMap));
        };
    }
}
