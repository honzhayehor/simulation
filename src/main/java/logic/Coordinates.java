package logic;

import entities.Entity;

public class Coordinates {
    private final int x;
    private final int y;
    private Entity entity;
    private Entity backedEntity;

    Coordinates(int x, int y, Entity entity) {
        this.x = x;
        this.y = y;
        this.entity = entity;
    }
    Coordinates(int x, int y) {
        this(x, y, null);
    }

    public int getX() {return x;}
    public int getY() {return y;}

    public boolean isEmpty() { return (entity == null) ? true: false;}

    public Entity getEntity() {return entity;}

    public void setEntity(Entity newEntity) {
        if (this.entity.isPassable()) {
            backedEntity = entity;
        }
        entity = newEntity;
    }

    public void getBackedEntityBack() {
        if (backedEntity != null) {
            entity =  backedEntity;
        }
    }


}
