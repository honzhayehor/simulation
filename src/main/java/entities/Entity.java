package entities;

import logic.Coordinates;

public abstract class Entity {
    private final boolean walkable;
    private Coordinates coordinates;

    protected Entity(Coordinates coordinates, boolean walkable) {
        this.coordinates = coordinates;
        this.walkable = walkable;
    }

    public abstract String getAvatar();

    public boolean isWalkable() {
        return !walkable;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
