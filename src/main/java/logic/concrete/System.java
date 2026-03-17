package logic.concrete;

import enviroment.Cell;
import enviroment.EntitiesFactory;
import enviroment.RandomCoordinatesGenerator;
import enviroment.WorldMap;
import logic.interfaces.Pathfinder;
import logic.config.CreationConfig;
import rendering.Renderer;
import units.abstraction.Entity;
import units.concrete.*;

import java.util.*;

public class System {

    private final WorldMap worldMap;
    private final Renderer renderer;
    private final Pathfinder pathfinder;
    private final List<Entity> entities;
    private final int moveCountEnd;

    public System(WorldMap worldMap, Renderer renderer, Pathfinder pathfinder, int moveCountEnd) {
        this.worldMap = worldMap;
        this.renderer = renderer;
        this.pathfinder = pathfinder;
        this.entities = new ArrayList<>();
        this.moveCountEnd = moveCountEnd;
    }

    public void generateEntities(List<CreationConfig> instruction) {
        int maxEntities = worldMap.getWidth() * worldMap.getHeight();
        if (instruction.size() > maxEntities) {
            throw new IllegalArgumentException(
                    "Too many entities: " + instruction.size() + " exceeds map capacity of " + maxEntities
            );
        }

        for (CreationConfig config : instruction) {
            Entity entity = EntitiesFactory.getEntity(config, worldMap, pathfinder).orElseThrow(() -> new IllegalArgumentException("Entity does not exist"));
            entities.add(entity);
        }
        addEntitiesToWorldMap();
    }

    private void addEntitiesToWorldMap() {
        int maxX = worldMap.getWidth() - 1;
        int maxY = worldMap.getHeight() - 1;
        RandomCoordinatesGenerator rcg = new RandomCoordinatesGenerator(0, maxX, 0, maxY);
        for (Entity entity : entities) {
            Cell cell = worldMap.cellOf(rcg.nextX(), rcg.nextY())
                    .orElseThrow(() -> new IllegalArgumentException("Cell with these coordinates does not exist"));
            worldMap.addEntityToCell(cell, entity);
        }
    }
}
