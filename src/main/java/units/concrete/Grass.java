package units.concrete;

import units.abstraction.Entity;
import units.configs.Energy;
import units.interfaces.Edible;
import units.interfaces.Passable;

public class Grass extends Entity implements Edible, Passable {
    @Override
    public String getRepresentation() {
        return "🌿";
    }
    @Override
    public int getEnergy() {
        return Energy.GRASS.getEnergy();
    }

    @Override
    public boolean isPassable() {
        return true;
    }
}
