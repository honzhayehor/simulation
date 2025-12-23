package entities;

import attributes.Edible;
import logic.Cell;
import logic.Energy;
import attributes.Renderable;

public class Grass extends Entity implements Edible, Renderable {

    public Grass(Cell cell) {
        super(cell, true);
    }

    @Override
    public String getAvatar() {
        return "\uD83C\uDF3F";
    }

    @Override
    public int energy() {
        return Energy.GRASS.getEnergy();
    }
}
