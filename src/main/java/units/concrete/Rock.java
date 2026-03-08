package units.concrete;

import units.abstraction.Entity;

public class Rock extends Entity {
    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public String getRepresentation() {
        return "🪨";
    }
}
