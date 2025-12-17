package logic;

import entities.Entity;

import java.util.Objects;

public class Cell {
    private final int x;
    private final int y;
    private Entity entity;
    private Entity backedEntity;

    public Cell(int x, int y, Entity entity) {
        this.x = x;
        this.y = y;
        this.entity = entity;
    }
    public Cell(int x, int y) {
        this(x, y, null);
    }

    public int x() {return x;}
    public int y() {return y;}

    public boolean isEmpty() { return entity == null;}

    public Entity getEntity() {return entity;}

    public void setEntity(Entity newEntity) {
        entity = newEntity;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cell that)) return false;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
