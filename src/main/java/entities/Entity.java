package entities;

import logic.Coordinates;

public abstract class Entity {
    private boolean walkable;
    public Coordinates coordinates;


    public abstract void setCoordinates();
    public boolean blocksMovement() {return this.walkable;}
}
