package entities;

import logic.Coordinates;
import logic.Species;

public abstract class Creature extends Entity{
    protected int hp;
    protected int speed;

    protected Creature(Coordinates coordinates, boolean walkable, Species species) {
        super(coordinates, walkable);
        this.hp = species.hp();
        this.speed = species.speed();
    }
}
