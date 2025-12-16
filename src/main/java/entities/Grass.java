package entities;

import attributes.Edible;
import logic.Coordinates;
import logic.Energy;

public class Grass extends Entity implements Edible {

    public Grass(Coordinates coordinates) {
        super(coordinates, true);
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
