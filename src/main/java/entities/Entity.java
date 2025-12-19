package entities;

import logic.Cell;

import java.util.Objects;

public abstract class Entity {
    private final boolean walkable;
    //private final int EID;
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

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Entity entity)) return false;
        return Objects.equals(cell, entity.cell);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cell);
    }
}
