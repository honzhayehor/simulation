package entities;

import logic.Cell;
import logic.IDs;

import java.util.Objects;

public abstract class Entity {
    private final boolean walkable;
    private final int EID = IDs.assignID();
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

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getEID() { return EID; }

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
