package entities;

import logic.Cell;

public abstract class Entity {
    private final boolean walkable;
    private Cell cell;

    protected Entity(Cell cell, boolean walkable) {
        this.cell = cell;
        this.walkable = walkable;
    }

    public abstract String getAvatar();

    public boolean isWalkable() {
        return !walkable;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCoordinates(Cell cell) {
        this.cell = cell;
    }
}
