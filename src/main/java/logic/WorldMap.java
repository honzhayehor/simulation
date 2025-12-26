package logic;

import entities.Entity;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public final class WorldMap {
    private Map<Cell, Entity> worldMap;
    private final int width;
    private final int height;

    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;
        worldMap = new HashMap<>();
        populateMap(this.width, this.height, worldMap);
    }

    private void populateMap(int width, int height, Map<Cell, Entity> map) {
        for (int i = 1; i <= width; i++) {
            for (int j = 1; j <= height; j++) {
                Cell cell = new Cell(i, j);
                map.put(cell, EntityFactory.getEntity(cell));
            }
        }
    }

    public void setEntityInCell(Cell cell, Entity entity) {
        // TODO: Implement
    }

    public Entity getEntityInCell(Cell cell) {
        // TODO: Implement
        return null;
    }


    public Cell getCell(int x, int y) {
        // TODO: Implement;
        return new Cell(1,1);
    }

    public boolean isFree(Cell cell) {
        return worldMap.get(cell) == null;
    }

    public void removeEntityFromCell(Cell cell, Entity entity) {
        // TODO: Implement
    }

    public int getWidth() { return width;}
    public int getHeight() { return height;}

}
