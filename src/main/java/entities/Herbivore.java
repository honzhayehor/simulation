package entities;

import logic.Coordinates;
import logic.Species;

public class Herbivore extends Creature {
    protected Herbivore(Coordinates coordinates, boolean walkable, Species species) {
        super(coordinates, walkable, species);
    }

    @Override
    public String getAvatar() {
        return "";
    }
}
