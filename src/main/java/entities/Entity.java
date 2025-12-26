package entities;

import logic.Cell;
import data.IDs;

public abstract class Entity {
    private final boolean walkable;
    protected final int eid = IDs.assignId();
    private Cell cell;

    protected Entity(Cell cell) {
        this.cell = cell;
        this.walkable = false;
    }

    public boolean isWalkable() {
        return !walkable;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getEID() { return eid; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity other)) return false;
        return this.eid == other.eid;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(eid);
    }
}
