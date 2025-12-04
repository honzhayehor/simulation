package entities;

import logic.Coordinates;

public abstract class Entity {
    private final boolean walkable;
    private Coordinates coordinates;

    public Entity(boolean walkable, Coordinates coordinates) {
        this.walkable = walkable;
        this.coordinates = coordinates;
    }

    public abstract String getAvatar();

    public boolean blocksMovement() {
        return !walkable;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
