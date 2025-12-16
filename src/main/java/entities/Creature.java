package entities;

import logic.Coordinates;
import logic.Species;

import java.util.List;

public abstract class Creature extends Entity{
    protected int hp;
    protected int speed;
    protected int vision;
    protected int hungerLossPerTurn;
    protected List<Coordinates> path;

    protected Creature(Coordinates coordinates, boolean walkable, Species species) {
        super(coordinates, walkable);
        this.hp = species.hp();
        this.speed = species.speed();
        this.vision = species.vision();
        this.hungerLossPerTurn = species.hungerLossPerTurn();
    }

    protected void starve() {
        hp -= hungerLossPerTurn;
    }

}
