package logic.concrete;

import enviroment.Cell;
import enviroment.WorldMap;
import logic.interfaces.Pathfinder;
import logic.config.CreationConfig;
import rendering.Renderer;
import units.abstraction.Entity;

import java.util.*;

public class System {

    private final WorldMap worldMap;
    private final Renderer renderer;
    private final Pathfinder pathfinder;
    private final List<Entity> entities;
    private final int moveCountEnd;
    private boolean canStart = false;

    public System(WorldMap worldMap, Renderer renderer, Pathfinder pathfinder, int moveCountEnd) {
        this.worldMap = worldMap;
        this.renderer = renderer;
        this.pathfinder = pathfinder;
        this.entities = new ArrayList<>();
        this.moveCountEnd = moveCountEnd;
    }

    public void start() {
        if (canStart) {
            updateGameStateForEachEntity();
        } else throw new UnsupportedOperationException("Cannot start game without initialized map. Please, provide instructions to this object");
    }

    private void updateGameStateForEachEntity() {
        for (int i = 0; i<moveCountEnd; i++ ) {
            List<Entity> entitiesOnMap = worldMap.getAllEntities();
            entitiesOnMap.forEach(Entity::makeMove);
            renderer.render(worldMap);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
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
        canStart = true;
    }

    private void addEntitiesToWorldMap() {
        int maxX = worldMap.getWidth() - 1;
        int maxY = worldMap.getHeight() - 1;
        RandomCoordinatesGenerator rcg = new RandomCoordinatesGenerator(0, maxX, 0, maxY);
        for (Entity entity : entities) {
            RandomCoordinatesGenerator.Coordinates coordinates = rcg.next();
            Cell cell = worldMap.cellOf(coordinates.x(), coordinates.y())
                    .orElseThrow(() -> new IllegalArgumentException("Cell with these coordinates does not exist"));
            worldMap.addEntityToCell(cell, entity);
        }
    }
}
