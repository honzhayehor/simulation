package logic;

import entities.Entity;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class WorldMap {
    private Map<Cell, List<Entity>> worldMap;
    private final int width;
    private final int height;

    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;
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
